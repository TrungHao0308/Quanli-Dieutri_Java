const canvas = document.getElementById("snow-canvas");
const ctx = canvas.getContext("2d");

let snowflakes = [];

function resizeCanvas() {
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;
}

window.addEventListener("resize", resizeCanvas);
resizeCanvas();

function createSnowflakes() {
  for (let i = 0; i < 100; i++) {
    snowflakes.push({
      x: Math.random() * canvas.width,
      y: Math.random() * canvas.height,
      r: Math.random() * 4 + 1,
      d: Math.random() * 1 + 0.5,
      color: Math.random() > 0.5 ? "#ffffff" : "#a3d3f5",
    });
  }
}

function drawSnowflakes() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  ctx.beginPath();
  for (let i = 0; i < snowflakes.length; i++) {
    let f = snowflakes[i];
    ctx.fillStyle = f.color;
    ctx.moveTo(f.x, f.y);
    ctx.arc(f.x, f.y, f.r, 0, Math.PI * 2, true);
  }
  ctx.fill();
  moveSnowflakes();
}

function moveSnowflakes() {
  for (let i = 0; i < snowflakes.length; i++) {
    let f = snowflakes[i];
    f.y += f.d;
    f.x += f.d * 0.3;

    if (f.y > canvas.height) {
      f.y = 0;
      f.x = Math.random() * canvas.width;
    }
  }
}

createSnowflakes();
setInterval(drawSnowflakes, 33);
