document.addEventListener('DOMContentLoaded', function () {
    const receptForm = document.getElementById('recept-form');
    const receptiContainer = document.getElementById('recepti-container');
    const searchInput = document.getElementById('search-input');
    let editingReceptId = null; // Spremenljivka za shranjevanje ID-ja recepta, ki ga urejamo
    let recepti = []; // Shranimo vse recepte v to spremenljivko

    // Funkcija za pridobivanje receptov iz API-ja
    function fetchRecepti() {
        fetch('http://localhost:8081/api/recepti')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                recepti = data; // Shrani vse recepte
                renderRecepti(recepti); // Prikaži vse recepte
            })
            .catch(error => console.error('Napaka pri pridobivanju receptov:', error));
    }

    // Funkcija za renderiranje receptov
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
                        </div>
                        <div class="card-footer">
                            <button class="btn btn-danger" onclick="deleteRecept('${recept.idje}')">Izbriši</button>
                            <button class="btn btn-warning" onclick="populateForm('${recept.idje}', '${recept.ime}', '${recept.opis}', '${recept.sestavine}', '${recept.navodila}', '${recept.slika}')">Uredi</button>
                        </div>
                    </div>
                </div>`;
        });
    }

    // Funkcija za dodajanje ali posodabljanje recepta
    receptForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const newRecept = {
            idje: document.getElementById('idje').value,
            ime: document.getElementById('ime').value,
            opis: document.getElementById('opis').value,
            sestavine: document.getElementById('sestavine').value,
            navodila: document.getElementById('navodila').value,
            slika: document.getElementById('slika').value
        };

        if (editingReceptId) {
            // Posodobi obstoječi recept
            fetch(`http://localhost:8081/api/recepti/${editingReceptId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newRecept),
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Napaka pri posodabljanju recepta');
                }
                return response.json();
            })
            .then(data => {
                fetchRecepti(); // Ponovno pridobi vse recepte
                receptForm.reset(); // Ponastavi formo
                editingReceptId = null; // Ponastavi ID za urejanje
            })
            .catch(error => console.error('Napaka pri posodabljanju recepta:', error));
        } else {
            // Dodaj nov recept
            fetch('http://localhost:8081/api/recepti', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newRecept),
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Napaka pri dodajanju recepta');
                }
                return response.json();
            })
            .then(data => {
                fetchRecepti(); // Ponovno pridobi vse recepte
                receptForm.reset(); // Ponastavi formo
            })
            .catch(error => console.error('Napaka pri dodajanju recepta:', error));
        }
    });

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

    // Funkcija za predhodno izpolnjevanje forme pri editiranju
    window.populateForm = function(idje, ime, opis, sestavine, navodila, slika) {
        document.getElementById('idje').value = idje;
        document.getElementById('ime').value = ime;
        document.getElementById('opis').value = opis;
        document.getElementById('sestavine').value = sestavine;
        document.getElementById('navodila').value = navodila;
        document.getElementById('slika').value = slika;
        editingReceptId = idje; // Nastavi ID recepta, ki ga urejamo
    }

    // Dodaj dogodek za iskanje
    searchInput.addEventListener('input', function () {
        const searchTerm = searchInput.value.toLowerCase(); // Pridobi iskalni niz
        const filteredRecepti = recepti.filter(recept => recept.ime.toLowerCase().includes(searchTerm)); // Filtriraj recepte
        renderRecepti(filteredRecepti); // Prikaži filtrirane recepte
    });

    // Inicializacija - pridobi recepte ob nalaganju strani
    fetchRecepti();
});
