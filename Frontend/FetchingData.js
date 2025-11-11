document.addEventListener("DOMContentLoaded", function () {
  const receptForm = document.getElementById("recept-form");
  const receptiContainer = document.getElementById("recepti-container");
  const searchInput = document.getElementById("search-input");
  let recepti = [];

  // 🔹 Pridobi vse recepte
  function fetchRecepti() {
    fetch("http://localhost:8081/api/recepti")
      .then((res) => {
        if (!res.ok) throw new Error("Napaka pri pridobivanju receptov");
        return res.json();
      })
      .then((data) => {
        recepti = data;
        renderRecepti(recepti);
      })
      .catch((err) => console.error(err));
  }

  // 🔹 Prikaži recepte
  function renderRecepti(receptiToRender) {
    receptiContainer.innerHTML = "";
    if (!receptiToRender || receptiToRender.length === 0) {
      receptiContainer.innerHTML = `<p class="text-center mt-3">Ni receptov za prikaz.</p>`;
      return;
    }

    receptiToRender.forEach((r) => {
      receptiContainer.innerHTML += `
        <div class="col-md-4 mb-4">
          <div class="card h-100 shadow-sm">
            <img src="${r.slika || 'https://via.placeholder.com/300x200'}" class="card-img-top" alt="Slika recepta">
            <div class="card-body">
              <h5 class="card-title">${r.ime}</h5>
              <p class="card-text">${r.opis || ''}</p>

              <h6 class="mt-2">Sestavine:</h6>
              <p id="sestavine-${r.idje}" class="sestavine-prikaz">${r.sestavine || ''}</p>

              <h6 class="mt-2">Navodila:</h6>
              <p>${r.navodila || ''}</p>

              <div class="d-flex gap-2 flex-wrap mt-3">
                <button class="btn btn-danger btn-sm" onclick="deleteRecept('${r.idje}')">Izbriši</button>
                <button class="btn btn-warning btn-sm" onclick="populateForm(
                  '${r.idje}',
                  '${escapeQuotes(r.ime)}',
                  '${escapeQuotes(r.opis)}',
                  '${escapeQuotes(r.sestavine)}',
                  '${escapeQuotes(r.navodila)}',
                  '${escapeQuotes(r.slika)}',
                  ${r.kalorije || 0},
                  ${r.proteini || 0},
                  ${r.karbohidrati || 0},
                  ${r.mascobe || 0},
                  ${r.vlaknine || 0}
                )">Uredi</button>
                <button class="btn btn-success btn-sm" onclick="exportToPDF('${r.idje}')">Izvozi PDF</button>
              </div>

              <div class="d-inline-flex align-items-center mt-3 w-100">
                <label for="porcije-${r.idje}" class="me-2 mb-0">Porcije:</label>
                <input type="number" id="porcije-${r.idje}" value="4" min="1" max="20" step="1" class="form-control w-50 me-2">
                <button class="btn btn-primary btn-sm" onclick="prilagodiSestavine('${r.idje}')">Prilagodi</button>
              </div>
            </div>
          </div>
        </div>
      `;
    });
  }

  // 🔹 Escape vrednosti za JS
  function escapeQuotes(text) {
    return text ? text.replace(/'/g, "\\'").replace(/"/g, "&quot;") : "";
  }

  // 🔹 Napolni obrazec za urejanje
  window.populateForm = function (idje, ime, opis, sestavine, navodila, slika, kalorije=0, proteini=0, karbohidrati=0, mascobe=0, vlaknine=0) {
    document.getElementById("idje").value = idje;
    document.getElementById("ime").value = ime;
    document.getElementById("opis").value = opis;
    document.getElementById("sestavine").value = sestavine;
    document.getElementById("navodila").value = navodila;
    document.getElementById("slika").value = slika;
    document.getElementById("kalorije").value = kalorije;
    document.getElementById("proteini").value = proteini;
    document.getElementById("karbohidrati").value = karbohidrati;
    document.getElementById("mascobe").value = mascobe;
    document.getElementById("vlaknine").value = vlaknine;
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  // 🔹 Dodaj ali posodobi recept
  receptForm.addEventListener("submit", function (e) {
    e.preventDefault();

    const idje = document.getElementById("idje").value.trim();
    const ime = document.getElementById("ime").value.trim();
    const opis = document.getElementById("opis").value.trim();
    const sestavine = document.getElementById("sestavine").value.trim();
    const navodila = document.getElementById("navodila").value.trim();
    const slika = document.getElementById("slika").value.trim();
    const kalorije = parseInt(document.getElementById("kalorije").value) || 0;
    const proteini = parseFloat(document.getElementById("proteini").value) || 0;
    const karbohidrati = parseFloat(document.getElementById("karbohidrati").value) || 0;
    const mascobe = parseFloat(document.getElementById("mascobe").value) || 0;
    const vlaknine = parseFloat(document.getElementById("vlaknine").value) || 0;

    if (!ime || !opis || !sestavine || !navodila) {
      alert("Prosim, izpolnite vsa polja!");
      return;
    }

    const recept = { ime, opis, sestavine, navodila, slika, kalorije, proteini, karbohidrati, mascobe, vlaknine };

    const method = idje ? "PUT" : "POST";
    const url = idje
      ? `http://localhost:8081/api/recepti/${idje}`
      : "http://localhost:8081/api/recepti";

    fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(recept),
    })
      .then(async (res) => {
        if (!res.ok) {
          const text = await res.text();
          throw new Error("Napaka pri shranjevanju recepta: " + text);
        }
        receptForm.reset();
        document.getElementById("idje").value = "";
        fetchRecepti();
      })
      .catch((err) => console.error(err));
  });

  // 🔹 Prilagodi sestavine glede na porcijo
  window.prilagodiSestavine = function (idje) {
    const porcijInput = document.getElementById(`porcije-${idje}`);
    const steviloPorcij = parseInt(porcijInput.value);
    if (!steviloPorcij || steviloPorcij < 1) return;

    const recept = recepti.find(r => r.idje === idje);
    if (!recept) return;

    const prilagojeneSestavine = recept.sestavine.split(",").map(s => {
      const [kolicina, ...ostalo] = s.trim().split(" ");
      const novaKolicina = isNaN(parseFloat(kolicina)) ? kolicina : ((parseFloat(kolicina)*steviloPorcij)/4).toFixed(2);
      return `${novaKolicina} ${ostalo.join(" ")}`;
    }).join(", ");

    const elem = document.getElementById(`sestavine-${idje}`);
    if (elem) {
      elem.innerText = prilagojeneSestavine;
      elem.classList.add("sestavine-posodobljene");
      setTimeout(()=>elem.classList.remove("sestavine-posodobljene"), 1500);
    }
  };

  // 🔹 Izbriši recept
  window.deleteRecept = function(idje) {
    if (!confirm("Ali res želite izbrisati ta recept?")) return;

    fetch(`http://localhost:8081/api/recepti/${idje}`, { method: "DELETE" })
      .then(res => { if(!res.ok) throw new Error("Napaka pri brisanju"); fetchRecepti(); })
      .catch(err => console.error(err));
  };

  // 🔹 Izvozi PDF
  window.exportToPDF = function(idje) {
    const recept = recepti.find(r => r.idje === idje);
    if (!recept) return;

    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();
    doc.setFont("helvetica","bold");
    doc.setFontSize(22);
    doc.text("Recept: "+recept.ime,20,20);
    doc.setFont("helvetica","normal");
    doc.setFontSize(14);
    doc.text("Opis:",20,35);
    doc.text(recept.opis || "",20,43);
    doc.text("Sestavine:",20,60);
    doc.text(recept.sestavine || "",20,68,{maxWidth:170});
    doc.text("Navodila:",20,95);
    doc.text(recept.navodila || "",20,103,{maxWidth:170});
    doc.save(`${recept.ime}.pdf`);
  };

  // 🔹 Iskanje
  searchInput.addEventListener("input", () => {
    const q = searchInput.value.toLowerCase();
    renderRecepti(recepti.filter(r => r.ime.toLowerCase().includes(q)));
  });

  // 🔹 Inicializacija
  fetchRecepti();
});
