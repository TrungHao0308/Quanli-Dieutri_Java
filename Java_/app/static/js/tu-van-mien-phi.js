document.addEventListener("DOMContentLoaded", function () {
  // Form submission handling
  const consultationForm = document.getElementById("consultationForm");

  consultationForm.addEventListener("submit", function (e) {
    e.preventDefault();

    // Collect form data
    const formData = {
      fullname: document.getElementById("fullname").value,
      phone: document.getElementById("phone").value,
      email: document.getElementById("email").value,
      question: document.getElementById("question").value,
    };

    // Here you would typically send the data to your server
    // For this example, we'll just show an alert
    alert(
      "Cảm ơn bạn đã gửi yêu cầu tư vấn! Chúng tôi sẽ liên hệ với bạn trong thời gian sớm nhất."
    );

    // Reset form
    consultationForm.reset();
  });
});
