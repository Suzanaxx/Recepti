document.addEventListener("DOMContentLoaded", function () {
  const receptForm = document.getElementById("recept-form");
  const receptiContainer = document.getElementById("recepti-container");
  const searchInput = document.getElementById("search-input");
  let recepti = []; // Shranimo vse recepte tukaj

  // Funkcija za pridobivanje receptov iz API-ja
  function fetchRecepti() {
    fetch("http://localhost:8081/api/recepti")
      .then((response) => response.json())
      .then((data) => {
        recepti = data;
        renderRecepti(recepti);
      })
      .catch((error) =>
        console.error("Napaka pri pridobivanju receptov:", error)
      );
  }
  window.prilagodiSestavine = function (recipeId) {
    const porcijInput = document.getElementById(`porcije-${recipeId}`);
    const steviloPorcij = parseInt(porcijInput.value);

    if (!steviloPorcij || steviloPorcij < 1) {
      alert("Vnesite veljavno število porcij (vsaj 1).");
      return;
    }

    const recept = recepti.find((r) => r.id === recipeId);
    if (!recept) {
      console.error("Recept ni najden!");
      return;
    }

    // Prilagoditev sestavin
    const prilagojeneSestavine = recept.sestavine
      .split(",")
      .map((sestavina) => {
        // Poskusi razbrati številko in enoto na začetku
        const [kolicina, ...ostalo] = sestavina.trim().split(" ");
        const novaKolicina = isNaN(parseFloat(kolicina))
          ? kolicina
          : ((parseFloat(kolicina) * steviloPorcij) / 4).toFixed(2);
        return `${novaKolicina} ${ostalo.join(" ")}`;
      })
      .join(", ");

    // Posodobitev prikaza sestavin
    const sestavineElement = document.getElementById(`sestavine-${recipeId}`);
    if (sestavineElement) {
        sestavineElement.innerText = prilagojeneSestavine;
        sestavineElement.classList.add("sestavine-posodobljene"); // Dodaj poudarek
        setTimeout(() => sestavineElement.classList.remove("sestavine-posodobljene"), 1500); // Odstrani poudarek po 1.5 sekunde
    }
  }

  // Funkcija za prikaz receptov
  function renderRecepti(receptiToRender) {
    receptiContainer.innerHTML = "";
    receptiToRender.forEach((recept) => {
        receptiContainer.innerHTML += `
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <img src="${recept.slika}" class="card-img-top" alt="Slika recepta">
                    <div class="card-body">
                        <h5 class="card-title">${recept.ime}</h5>
                        <p class="card-text">${recept.opis}</p>
                        <h6 class="mt-2">Sestavine:</h6>
                        <p id="sestavine-${recept.id}" class="sestavine-prikaz">${recept.sestavine}</p>
                        <h6 class="mt-2">Navodila:</h6>
                        <p>${recept.navodila}</p>

                        <div class="rating-container" style="max-width: 50%; margin: auto;">
                            <label for="rating-select">Rate this Recipe:</label>
                            <select id="rating-select-${recept.id}" class="form-select">
                                <option value="" selected disabled>Select Rating</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                            <button class="btn btn-primary mt-2" onclick="submitRating(${recept.id})">Submit Rating</button>
                            <div id="rating-feedback-${recept.id}" class="mt-2" style="font-size: 0.9em;"></div>
                        </div>

                        <!-- Komentarji -->
                        <h6 class="mt-4">Komentarji:</h6>
                        <div id="comments-${recept.id}" class="mb-3">
                            <!-- Komentarji bodo prikazani tukaj -->
                        </div>
                        <textarea id="comment-input-${recept.id}" placeholder="Dodaj komentar" class="form-control mb-2"></textarea>
                        <button class="btn btn-primary" onclick="addComment(${recept.id}, document.getElementById('comment-input-${recept.id}').value)">Dodaj komentar</button>
                    </div>
                    
                    <div class="card-footer">
                        <button class="btn btn-danger" onclick="deleteRecept('${recept.idje}')">Izbriši</button>
                        <button class="btn btn-warning" onclick="populateForm('${recept.idje}', '${recept.ime}', '${recept.opis}', '${recept.sestavine}', '${recept.navodila}', '${recept.slika}')">Uredi</button>
                        <button class="btn btn-success" onclick="exportToPDF('${recept.idje}')">Izvozi PDF</button>

                        <!-- Gumb za prilagoditev števila porcij -->
                        <div class="d-inline-flex align-items-center mt-2">
                            <label for="porcije-${recept.id}" class="me-2 mb-0">Porcije:</label>
                            <input type="number" id="porcije-${recept.id}" value="4" min="1" max="20" step="1" class="form-control w-50 me-2">
                            <button class="btn btn-primary" onclick="prilagodiSestavine(${recept.id})">Prilagodi</button>
                        </div>
                    </div>
                </div>
            </div>`;

        fetchComments(recept.id); // Pridobi komentarje za vsak recept
        submitRating(recept.id); // Submit rating for each recipe
        populateForm(
            recept.idje,
            recept.ime,
            recept.opis,
            recept.sestavine,
            recept.navodila,
            recept.slika
        ); // Fill form for each recipe
    });
}

  // Funkcija za dodajanje novega komentarja
  function fetchComments(recipeId) {
    fetch(`http://localhost:8081/api/recepti/${recipeId}/comments`)
      .then((response) => response.json())
      .then((comments) => {
        const commentsContainer = document.getElementById(
          `comments-${recipeId}`
        );
        commentsContainer.innerHTML = comments
          .map(
            (comment) => `
                    <p><strong>Uporabnik ${comment.userId}:</strong> ${comment.comment}</p>
                `
          )
          .join("");
      })
      .catch((error) =>
        console.error("Napaka pri pridobivanju komentarjev:", error)
      );
  }

  function addComment(recipeId, content) {
    if (!content.trim()) {
      alert("Comment cannot be empty!");
      return;
    }

    const userId = JSON.parse(localStorage.getItem("loggedInUser")).id; // Get logged-in user's ID

    fetch(`http://localhost:8081/api/recepti/${recipeId}/comments`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: userId,
        comment: content,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Error adding comment!");
        }
        return response.json();
      })
      .then(() => {
        fetchComments(recipeId); // Refresh comments
        document.getElementById(`comment-input-${recipeId}`).value = ""; // Clear input field
      })
      .catch((error) => console.error("Error adding comment:", error));
  }

  // Dodaj funkcijo v globalni obseg
  window.addComment = addComment;

  // Funkcija za brisanje recepta
  window.deleteRecept = function (idje) {
    fetch(`http://localhost:8081/api/recepti/${idje}`, { method: "DELETE" })
      .then(() => fetchRecepti())
      .catch((error) => console.error("Napaka pri brisanju recepta:", error));
  };
  window.submitRating = function (recipeId) {
    const ratingSelect = document.getElementById(`rating-select-${recipeId}`);
    const feedbackDiv = document.getElementById(`rating-feedback-${recipeId}`);
    const rating = ratingSelect.value;

    if (!rating) {
      feedbackDiv.innerText = "Please select a rating!";
      feedbackDiv.style.color = "red";
      return;
    }

    // Retrieve the logged-in user's data from localStorage
    const loggedInUser = JSON.parse(localStorage.getItem("loggedInUser"));

    if (!loggedInUser || !loggedInUser.id) {
      feedbackDiv.innerText = "User not logged in!";
      feedbackDiv.style.color = "red";
      return;
    }

    const userId = loggedInUser.id; // Use the correct user ID from the logged-in user

    // Debugging: Log the values being sent
    console.log(
      `Submitting rating for recipeId: ${recipeId}, userId: ${userId}, rating: ${rating}`
    );

    fetch(`http://localhost:8081/api/recepti/${recipeId}/ratings`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ userId, rating }),
    })
      .then((response) => {
        if (!response.ok) {
          console.error(`Failed to submit rating. Status: ${response.status}`);
          throw new Error("Failed to submit rating");
        }
        return response.text();
      })
      .then((message) => {
        console.log(`Server Response: ${message}`);
        feedbackDiv.innerText = "Rating submitted successfully!";
        feedbackDiv.style.color = "green";
      })
      .catch((error) => {
        feedbackDiv.innerText = "Error submitting rating. Please try again.";
        feedbackDiv.style.color = "red";
        console.error("Error submitting rating:", error);
      });
  };

  // Funkcija za izvoz recepta v PDF
  window.exportToPDF = function (idje) {
    const recept = recepti.find((r) => r.idje === idje); // Find the specific recipe
    if (!recept) {
      console.error("Recept ni najden!");
      return;
    }

    // Use jsPDF to generate the PDF
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    const marginLeft = 20;
    const lineHeight = 10;
    let yPosition = 20;

    // Add Recipe Title
    doc.setFont("helvetica", "bold");
    doc.setFontSize(22);
    doc.text("Recept: " + recept.ime, marginLeft, yPosition);

    yPosition += lineHeight + 10;

    // Add Description
    doc.setFont("helvetica", "normal");
    doc.setFontSize(14);
    doc.text("Opis:", marginLeft, yPosition);
    yPosition += lineHeight;
    doc.text(recept.opis || "Ni opisa na voljo", marginLeft, yPosition, {
      maxWidth: 170,
    });

    yPosition += lineHeight * 2;

    // Add Ingredients (Sestavine in rows)
    doc.setFont("helvetica", "bold");
    doc.text("Sestavine:", marginLeft, yPosition);
    yPosition += lineHeight;
    doc.setFont("helvetica", "normal");
    const ingredients = recept.sestavine
      ? recept.sestavine.split("\n")
      : ["Ni sestavin na voljo"];
    ingredients.forEach((ingredient) => {
      doc.text("- " + ingredient.trim(), marginLeft, yPosition);
      yPosition += lineHeight;
    });

    yPosition += lineHeight;

    // Add Instructions
    doc.setFont("helvetica", "bold");
    doc.text("Navodila:", marginLeft, yPosition);
    yPosition += lineHeight;
    doc.setFont("helvetica", "normal");
    doc.text(recept.navodila || "Ni navodil na voljo", marginLeft, yPosition, {
      maxWidth: 170,
    });

    yPosition += lineHeight * 3;

    // Add Image (if available)
    if (recept.slika) {
      const img = new Image();
      img.src = recept.slika;
      img.onload = function () {
        const imgWidth = 80;
        const imgHeight = 80;
        doc.addImage(img, "JPEG", marginLeft, yPosition, imgWidth, imgHeight);
        doc.save(recept.ime + ".pdf");
      };
    } else {
      doc.save(recept.ime + ".pdf");
    }
  };

  // Funkcija za urejanje recepta (polni formo)
  window.populateForm = function (idje, ime, opis, sestavine, navodila, slika) {
    document.getElementById("idje").value = idje;
    document.getElementById("ime").value = ime;
    document.getElementById("opis").value = opis;
    document.getElementById("sestavine").value = sestavine;
    document.getElementById("navodila").value = navodila;
    document.getElementById("slika").value = slika;
  };

  // Inicializacija
  fetchRecepti();
});
