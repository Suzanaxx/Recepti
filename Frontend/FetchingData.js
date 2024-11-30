document.addEventListener('DOMContentLoaded', function () {
    const userId = 1; // Privzeti uporabnik za testiranje
    const receptForm = document.getElementById('recept-form');
    const receptiContainer = document.getElementById('recepti-container');
    const searchInput = document.getElementById('search-input');
    let recepti = []; // Shranimo vse recepte tukaj

    // Funkcija za izvoz recepta v PDF
    function exportToPDF(idje) {
        const recept = recepti.find(r => r.idje === idje);
        if (!recept) {
            console.error('Recept ni najden!');
            return;
        }

        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        const marginLeft = 20;
        const lineHeight = 10;
        let yPosition = 20;

        doc.setFont("helvetica", "bold");
        doc.setFontSize(22);
        doc.text("Recept: " + recept.ime, marginLeft, yPosition);

        yPosition += lineHeight + 10;

        doc.setFont("helvetica", "normal");
        doc.setFontSize(14);
        doc.text("Opis:", marginLeft, yPosition);
        yPosition += lineHeight;
        doc.text(recept.opis, marginLeft, yPosition, { maxWidth: 170 });

        yPosition += lineHeight * 2;

        doc.setFont("helvetica", "bold");
        doc.text("Sestavine:", marginLeft, yPosition);
        yPosition += lineHeight;
        doc.setFont("helvetica", "normal");
        doc.text(recept.sestavine, marginLeft, yPosition, { maxWidth: 170 });

        yPosition += lineHeight * 2;

        doc.setFont("helvetica", "bold");
        doc.text("Navodila:", marginLeft, yPosition);
        yPosition += lineHeight;
        doc.setFont("helvetica", "normal");
        doc.text(recept.navodila, marginLeft, yPosition, { maxWidth: 170 });

        yPosition += lineHeight * 3;

        if (recept.slika) {
            const img = new Image();
            img.src = recept.slika;
            img.onload = function () {
                const imgWidth = 80;
                const imgHeight = 80;
                doc.addImage(img, 'JPEG', marginLeft, yPosition, imgWidth, imgHeight);
                doc.save(recept.ime + ".pdf");
            };
        } else {
            doc.save(recept.ime + ".pdf");
        }
    }

    window.exportToPDF = exportToPDF;

    // Funkcija za pridobivanje receptov iz API-ja
    function fetchRecepti() {
        fetch('http://localhost:8081/api/recepti')
            .then(response => response.json())
            .then(data => {
                recepti = data; // Shrani vse recepte
                renderRecepti(recepti); // Prikaži vse recepte
            })
            .catch(error => console.error('Napaka pri pridobivanju receptov:', error));
    }

    // Funkcija za prikaz receptov
    function renderRecepti(receptiToRender) {
        receptiContainer.innerHTML = '';
        receptiToRender.forEach(recept => {
            receptiContainer.innerHTML += `
                <div class="col-md-4 mb-4">
                    <div class="card h-100">
                        <img src="${recept.slika}" class="card-img-top" alt="Slika recepta">
                        <div class="card-body">
                            <h5 class="card-title">${recept.ime}</h5>
                            <p class="card-text">${recept.opis}</p>
                            <h6 class="mt-2">Sestavine:</h6>
                            <p>${recept.sestavine}</p>
                            <h6 class="mt-2">Navodila:</h6>
                            <p>${recept.navodila}</p>

                            <!-- Komentarji -->
                            <h6 class="mt-4">Komentarji:</h6>
                            <div id="comments-${recept.id}" class="mb-3"></div>
                            <textarea id="comment-input-${recept.id}" placeholder="Dodaj komentar" class="form-control mb-2"></textarea>
                            <button class="btn btn-primary" onclick="addComment(${recept.id}, document.getElementById('comment-input-${recept.id}').value)">Dodaj komentar</button>

                            <!-- Zvezdice za ocenjevanje -->
                            <h6 class="mt-4">Ocenite recept:</h6>
                            <div id="rating-${recept.id}" class="rating">
                                <span class="star" data-value="1">&#9733;</span>
                                <span class="star" data-value="2">&#9733;</span>
                                <span class="star" data-value="3">&#9733;</span>
                                <span class="star" data-value="4">&#9733;</span>
                                <span class="star" data-value="5">&#9733;</span>
                            </div>
                            <div id="average-rating-${recept.id}" class="mt-2">
                                Povprečna ocena: <span id="avg-rating-${recept.id}">N/A</span>
                            </div>
                        </div>
                        <div class="card-footer">
                            <button class="btn btn-danger" onclick="deleteRecept('${recept.idje}')">Izbriši</button>
                            <button class="btn btn-warning" onclick="populateForm('${recept.idje}', '${recept.ime}', '${recept.opis}', '${recept.sestavine}', '${recept.navodila}', '${recept.slika}')">Uredi</button>
                        </div>
                    </div>
                </div>`;
        
            fetchComments(recept.id); // Pridobi komentarje za vsak recept
            fetchAverageRating(recept.id); // Pridobi povprečno oceno za vsak recept
            addRatingEventListeners(recept.id); // Dodaj dogodek za zvezdice
        });
    }

    // Funkcija za pridobivanje komentarjev za recept
    function fetchComments(recipeId) {
        fetch(`http://localhost:8081/api/recepti/${recipeId}/comments`)
            .then(response => response.json())
            .then(comments => {
                const commentsContainer = document.getElementById(`comments-${recipeId}`);
                commentsContainer.innerHTML = comments.map(comment => `
                    <p><strong>Uporabnik ${comment.userId}:</strong> ${comment.comment}</p>
                `).join('');
            })
            .catch(error => console.error('Napaka pri pridobivanju komentarjev:', error));
    }
    
    function addComment(recipeId, content) {
        if (!content.trim()) {
            alert('Komentar ne sme biti prazen!');
            return;
        }

        fetch(`http://localhost:8081/api/recepti/${recipeId}/comments`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userId: userId,  // Uporabnik ID je 1 (statistično)
                comment: content,
            }),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Napaka pri dodajanju komentarja!');
                }
                return response.json();
            })
            .then(() => {
                fetchComments(recipeId); // Osveži komentarje
                document.getElementById(`comment-input-${recipeId}`).value = ''; // Počisti vnosno polje
            })
            .catch(error => console.error('Napaka pri dodajanju komentarja:', error));
    }

    // Funkcija za dodajanje ocene preko zvezdic
    function addRating(recipeId, rating) {
        fetch(`http://localhost:8081/api/recepti/${recipeId}/ratings`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userId: userId,  // Uporabnik ID je 1 (statistično)
                rating: rating,
            }),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Napaka pri dodajanju ocene!');
            }
            return response.json();
        })
        .then(() => {
            fetchAverageRating(recipeId); // Osveži povprečno oceno
        })
        .catch(error => console.error('Napaka pri dodajanju ocene:', error));
    }

    // Funkcija za pridobivanje povprečne ocene za recept
    function fetchAverageRating(recipeId) {
        fetch(`http://localhost:8081/api/recepti/${recipeId}/ratings`)
            .then(response => response.json())
            .then(ratings => {
                const averageRating = ratings.reduce((acc, curr) => acc + curr.rating, 0) / ratings.length;
                const avgElement = document.getElementById(`avg-rating-${recipeId}`);
                avgElement.textContent = isNaN(averageRating) ? "N/A" : averageRating.toFixed(1);
            })
            .catch(error => console.error('Napaka pri pridobivanju ocen:', error));
    }

    // Funkcija za dodajanje event listenerjev na zvezdice
    function addRatingEventListeners(recipeId) {
        const stars = document.querySelectorAll(`#rating-${recipeId} .star`);
        stars.forEach(star => {
            star.addEventListener('click', function () {
                const rating = parseInt(this.getAttribute('data-value'));
                addRating(recipeId, rating);  // Dodaj oceno
                updateStarRating(recipeId, rating);  // Posodobi zvezdice
            });

            // Prikaz zvezdic ob hoverju
            star.addEventListener('mouseover', function () {
                const ratingValue = parseInt(this.getAttribute('data-value'));
                highlightStars(recipeId, ratingValue);  // Poudari zvezdice ob hoverju
            });

            star.addEventListener('mouseleave', function () {
                clearHighlightedStars(recipeId);  // Odstrani poudarjene zvezdice po končanem hoverju
            });
        });
    }

    // Funkcija za posodabljanje napolnjenih zvezdic
    function updateStarRating(recipeId, rating) {
        const stars = document.querySelectorAll(`#rating-${recipeId} .star`);
        stars.forEach(star => {
            if (parseInt(star.getAttribute('data-value')) <= rating) {
                star.classList.add('checked');
            } else {
                star.classList.remove('checked');
            }
        });
    }

    // Funkcija za poudarjanje zvezdic med hoverjem
    function highlightStars(recipeId, ratingValue) {
        const stars = document.querySelectorAll(`#rating-${recipeId} .star`);
        stars.forEach(star => {
            if (parseInt(star.getAttribute('data-value')) <= ratingValue) {
                star.classList.add('checked');
            } else {
                star.classList.remove('checked');
            }
        });
    }

    // Funkcija za odstranjevanje poudarjenih zvezdic po končanem hoverju
    function clearHighlightedStars(recipeId) {
        const stars = document.querySelectorAll(`#rating-${recipeId} .star`);
        stars.forEach(star => {
            star.classList.remove('checked');
        });
    }

    // Funkcija za brisanje recepta
    window.deleteRecept = function(idje) {
        fetch(`http://localhost:8081/api/recepti/${idje}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Napaka pri brisanju recepta');
            }
            fetchRecepti(); // Ponovno pridobi vse recepte
        })
        .catch(error => console.error('Napaka pri brisanju recepta:', error));
    }

    // Inicializacija - pridobi recepte ob nalaganju strani
    fetchRecepti();
});
