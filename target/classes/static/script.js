
const video = document.getElementById('video');
const statusElement = document.getElementById('status');

// Function to update status with optional class for styling
function updateStatus(message, statusClass = '') {
  statusElement.textContent = message;
  statusElement.className = statusClass;
}

// Load the required models from the '/models' directory
updateStatus("Loading face recognition models...", "loading");
Promise.all([
  faceapi.nets.tinyFaceDetector.loadFromUri('./models'),
  faceapi.nets.faceLandmark68Net.loadFromUri('./models'),
  faceapi.nets.faceRecognitionNet.loadFromUri('./models'),
  faceapi.nets.faceExpressionNet.loadFromUri('./models')
]).then(() => {
  updateStatus("Models loaded successfully. Starting video...", "success");
  startVideo();
}).catch(error => {
  updateStatus("Error loading models. Please try again.", "error");
  console.error("Error loading models:", error);
});

let knownDescriptors = [];
let classId = getClassIdFromUrl();  // Get classId from URL
let studentId = getStudentIdFromUrl();  // Get studentId from URL
let attendanceMarked = false; // Flag to track attendance status

async function loadKnownDescriptors() {
  try {
    updateStatus("Loading known face descriptors...", "loading");
    const response = await fetch(`/api/students/${studentId}/faceDescriptors`);
    if (!response.ok) {
      throw new Error('Network response was not ok.');
    }

    const data = await response.json();
    if (typeof data === 'object' && data !== null) {
      const descriptorArray = Object.values(data);
      if (descriptorArray.length === 128) {
        knownDescriptors = [
          new faceapi.LabeledFaceDescriptors('student', [new Float32Array(descriptorArray)])
        ];
        updateStatus("Face descriptors loaded successfully.", "success");
      } else {
        updateStatus("Error: Incorrect descriptor length or missing descriptor.", "error");
      }
    } else {
      throw new Error('Expected an object but got a different type');
    }
  } catch (error) {
    updateStatus("Error loading face descriptors. Please try again.", "error");
    console.error('Error loading descriptors:', error);
  }
}

function startVideo() {
  navigator.mediaDevices.getUserMedia({ video: {} })
    .then(stream => {
      video.srcObject = stream;
      updateStatus("Video started. Preparing face recognition...", "loading");
    })
    .catch(err => {
      updateStatus("Error accessing the webcam. Please check your camera permissions ,or reload page", "error");
      console.error("Error accessing the webcam:", err);
    });

  loadKnownDescriptors();
}

video.addEventListener('play', () => {
  const canvas = faceapi.createCanvasFromMedia(video);
  document.body.append(canvas);
  const displaySize = { width: video.width, height: video.height };
  faceapi.matchDimensions(canvas, displaySize);

  updateStatus("Face recognition active. Please look at the camera.", "loading");

  setInterval(async () => {
    if (attendanceMarked) return;

    const detections = await faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions())
      .withFaceLandmarks()
      .withFaceDescriptors()
      .withFaceExpressions();

    const resizedDetections = faceapi.resizeResults(detections, displaySize);
    canvas.getContext('2d').clearRect(0, 0, canvas.width, canvas.height);

    faceapi.draw.drawDetections(canvas, resizedDetections);
    faceapi.draw.drawFaceLandmarks(canvas, resizedDetections);
    faceapi.draw.drawFaceExpressions(canvas, resizedDetections);

    if (detections.length === 0) {
      updateStatus("No face detected. Please move closer to the camera.", "error");
      return;
    }

    if (knownDescriptors.length > 0) {
      const faceMatcher = new faceapi.FaceMatcher(knownDescriptors, 1); //Detection Threshold
      const results = resizedDetections.map(d => faceMatcher.findBestMatch(d.descriptor));

      results.forEach((result, i) => {
        if (result.label !== 'unknown' && !attendanceMarked) {
          updateStatus("Face recognized. Marking attendance...", "success");
          markAttendance(studentId, classId);
          attendanceMarked = true;
          stopVideo();
        } else if (!attendanceMarked) {
          updateStatus("Face not recognized. Please try again.", "error");
        }
      });
    } else {
      updateStatus("Error: No known face data available. Please contact support.", "error");
    }
  }, 500);
});

async function markAttendance(studentId, classId) {
  try {
    updateStatus("Marking attendance...", "loading");
    const response = await fetch('/api/attendance/mark', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ studentId: studentId, classId: classId })
    });

    if (response.ok) {
      updateStatus("Attendance marked successfully. Redirecting...", "success");
      window.location.href = `success.html?message=Attendance marked successfully`;
    } else {
      updateStatus("Failed to mark attendance. Please try again.", "error");
    }
  } catch (error) {
    updateStatus("Error marking attendance. Please try again.", "error");
    console.error("Error marking attendance:", error);
  }
}

function stopVideo() {
  const stream = video.srcObject;
  if (stream) {
    stream.getTracks().forEach(track => track.stop());
  }
  video.srcObject = null;
  updateStatus("Video stopped. Attendance process complete.", "success");
}

function getClassIdFromUrl() {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get('classId');
}

function getStudentIdFromUrl() {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get('studentId');
}
