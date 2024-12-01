document.addEventListener('DOMContentLoaded', function () {
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

    // Funkcija za pridobivanje receptov iz API-ja
    function fetchRecepti() {
        fetch('http://localhost:8081/api/recepti')
            .then(response => response.json())
            .then(data => {
                recepti = data;
                renderRecepti(recepti);
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
                        </div>
                    </div>
                </div>`;
            fetchComments(recept.id); // Pridobi komentarje za vsak recept
        });
    }

    // Funkcija za dodajanje novega komentarja
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
            alert('Comment cannot be empty!');
            return;
        }
    
        const userId = JSON.parse(localStorage.getItem('loggedInUser')).id; // Get logged-in user's ID
    
        fetch(`http://localhost:8081/api/recepti/${recipeId}/comments`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userId: userId,
                comment: content,
            }),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error adding comment!');
                }
                return response.json();
            })
            .then(() => {
                fetchComments(recipeId); // Refresh comments
                document.getElementById(`comment-input-${recipeId}`).value = ''; // Clear input field
            })
            .catch(error => console.error('Error adding comment:', error));
    }
    

    // Dodaj funkcijo v globalni obseg
    window.addComment = addComment;

    // Funkcija za brisanje recepta
    window.deleteRecept = function (idje) {
        fetch(`http://localhost:8081/api/recepti/${idje}`, { method: 'DELETE' })
            .then(() => fetchRecepti())
            .catch(error => console.error('Napaka pri brisanju recepta:', error));
    };

    // Funkcija za urejanje recepta (polni formo)
    window.populateForm = function (idje, ime, opis, sestavine, navodila, slika) {
        document.getElementById('idje').value = idje;
        document.getElementById('ime').value = ime;
        document.getElementById('opis').value = opis;
        document.getElementById('sestavine').value = sestavine;
        document.getElementById('navodila').value = navodila;
        document.getElementById('slika').value = slika;
    };

    // Inicializacija
    fetchRecepti();
});