function initCamera() {

	const video = document.getElementById('webcam');
	const overlay = document.getElementById('overlay');
	const captureBtn = document.getElementById('captureBtn');
	const pictureInput = document.getElementById('picture');
	const hiddenCanvas = document.getElementById('canvas');
	const overlayCtx = overlay.getContext('2d');
	const cameraSelect = document.getElementById('cameraSelect');
	
	let cropWidth = 200;
	let cropHeight = 200;
	let cropX = 0;
	let cropY = 0;
	let currentStream;
	
	// Function to draw the overlay and crop square
	function drawOverlay() {
	    overlayCtx.clearRect(0, 0, overlay.width, overlay.height);
	
	    // Darken outside area
	    overlayCtx.fillStyle = "rgba(0,0,0,0.5)";
	    overlayCtx.fillRect(0, 0, overlay.width, overlay.height);
	
	    // Clear the crop square
	    overlayCtx.clearRect(cropX, cropY, cropWidth, cropHeight);
	
	    // Draw red square border
	    overlayCtx.strokeStyle = "#FF0000";
	    overlayCtx.lineWidth = 3;
	    overlayCtx.strokeRect(cropX, cropY, cropWidth, cropHeight);
	}
	
	// Function to get available cameras and populate dropdown
	function getCameras() {
	    navigator.mediaDevices.enumerateDevices()
	        .then(devices => {
	            const videoDevices = devices.filter(device => device.kind === 'videoinput');
	            cameraSelect.innerHTML = ''; // Clear previous options
	
	            if (videoDevices.length === 0) {
	                alert('No video devices found!');
	                return;
	            }
	
	            // Populate the dropdown with available video devices
	            videoDevices.forEach((device, index) => {
	                const option = document.createElement('option');
	                option.value = device.deviceId;
	                option.text = device.label || `Camera ${index + 1}`;
	                cameraSelect.appendChild(option);
	            });
	
	            // Automatically start the first camera if devices are found
	            startCamera(videoDevices[0].deviceId);
	        })
	        .catch(error => {
	            console.error('Error listing cameras:', error);
	        });
	}
	
	// Function to start the selected camera
	function startCamera(deviceId) {
	    if (currentStream) {
	        currentStream.getTracks().forEach(track => track.stop()); // Stop previous stream if any
	    }
	
	    navigator.mediaDevices.getUserMedia({
	        video: { deviceId: deviceId ? { exact: deviceId } : undefined }
	    })
	    .then(function (stream) {
	        currentStream = stream;
	        video.srcObject = stream;
	
	        // Only play after video metadata is loaded
	        video.onloadedmetadata = function () {
	            video.play();
	
	            const aspectRatio = video.videoWidth / video.videoHeight;
	
	            video.width = 300;
	            video.height = 300 / aspectRatio;
	
	            overlay.width = video.width;
	            overlay.height = video.height;
	
	            const side = Math.min(overlay.width, overlay.height) * 0.6; // 60% of smaller dimension
	            cropWidth = cropHeight = side;
	            cropX = (overlay.width - cropWidth) / 2;
	            cropY = (overlay.height - cropHeight) / 2;
	
	            drawOverlay();
	        };
	    })
	    .catch(function (error) {
	        console.error("Error accessing webcam:", error);
	    });
	}
	
	// Capture image when clicking the button
	captureBtn.addEventListener('click', function() {
	    const hiddenCtx = hiddenCanvas.getContext('2d');
	
	    // Scale between video display size and actual video size
	    const scaleX = video.videoWidth / video.width;
	    const scaleY = video.videoHeight / video.height;
	
	    const realCropX = cropX * scaleX;
	    const realCropY = cropY * scaleY;
	    const realCropWidth = cropWidth * scaleX;
	    const realCropHeight = cropHeight * scaleY;
	
	    hiddenCanvas.width = cropWidth;
	    hiddenCanvas.height = cropHeight;
	
	    hiddenCtx.drawImage(video, realCropX, realCropY, realCropWidth, realCropHeight, 0, 0, cropWidth, cropHeight);
	
	    hiddenCanvas.toBlob(function(blob) {
	        const file = new File([blob], "webcam-picture.png", { type: "image/png" });
	
	        const dataTransfer = new DataTransfer();
	        dataTransfer.items.add(file);
	
	        pictureInput.files = dataTransfer.files;
	    }, 'image/png');
	});
	
	// When user selects another camera
	cameraSelect.addEventListener('change', function() {
	    startCamera(cameraSelect.value);
	});
	
	// Initialize: get camera list and start the first camera
	navigator.mediaDevices.getUserMedia({ video: true })
	    .then(function(stream) {
	        // Camera permissions granted
	        getCameras();
	    })
	    .catch(function(error) {
	        console.error("Error getting camera permissions:", error);
	        alert("Please allow camera permissions to continue.");
	    });
		
}