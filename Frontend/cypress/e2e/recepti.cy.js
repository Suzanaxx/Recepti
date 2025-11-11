describe('Recepti Frontend Tests', () => {
  // Prijava pred vsakim testom
  beforeEach(() => {
    cy.visit('http://localhost:3000/login.html');

    // Vnesi test uporabnika
    cy.get('#username').type('testuser');
    cy.get('#password').type('password');
    cy.get('#login-btn').click();

    // Preveri, da je bil redirect na profile.html
    cy.url().should('include', '/profile.html');

    // Nato pojdi na index.html, kjer so recepti
    cy.visit('http://localhost:3000/index.html');
  });

  it('Dodajanje novega recepta', () => {
    // Vnesi podatke v formo
    cy.get('#ime').type('Čokoladna torta');
    cy.get('#opis').type('Sočna torta s čokoladnim prelivom');
    cy.get('#sestavine').type('Moka, sladkor, jajca, čokolada');
    cy.get('#navodila').type('Zmešaj sestavine in peci 30 min pri 180°C');
    cy.get('#slika').type('https://via.placeholder.com/150');
    cy.get('#kalorije').type('450');
    cy.get('#proteini').type('8');
    cy.get('#karbohidrati').type('60');
    cy.get('#mascobe').type('20');
    cy.get('#vlaknine').type('3');

    // Sproži submit forme
    cy.get('#recept-form').submit();

    // Preveri, da se recept prikaže v seznamu
    cy.contains('Čokoladna torta', { timeout: 5000 }).should('exist');
  });

  it('Urejanje obstoječega recepta', () => {
    // Klikni gumb "Uredi" prvega recepta
    cy.get('.btn-warning').first().click();

    // Preveri, da se polja napolnijo
    cy.get('#ime').invoke('val').should('not.be.empty');

    // Spremeni opis
    cy.get('#opis').clear().type('Torta s temno čokolado in malinami');

    // Pošlji spremembe
    cy.get('#recept-form').submit();

    // Počakaj, da se posodobljen recept prikaže
    cy.contains('Torta s temno čokolado in malinami', { timeout: 5000 }).should('exist');
  });

  it('Iskanje receptov po imenu', () => {
    cy.get('#search-input').type('torta');

    // Uporabi keyup za sprožitev filtra
    cy.get('#search-input').trigger('input');

    cy.get('#recepti-container .card-title:visible').each(($el) => {
      expect($el.text().toLowerCase()).to.include('torta');
    });

    // Počisti iskalno polje
    cy.get('#search-input').clear().trigger('input');

    // Preveri, da so vsi recepti spet vidni
    cy.get('#recepti-container .card-title').should('have.length.greaterThan', 0);
  });
});
