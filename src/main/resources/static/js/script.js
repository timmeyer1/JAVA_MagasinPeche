// Récupère tous les liens de la barre latérale
const sidebarLinks = document.querySelectorAll('.profil-sidebar a');
// Récupère toutes les sections de contenu
const contentSections = document.querySelectorAll('.content-section');

// Ajoute un écouteur d'événements pour chaque lien de la barre latérale
sidebarLinks.forEach(link => {
    link.addEventListener('click', function (event) {
        event.preventDefault();

        const targetSection = link.getAttribute('data-section');

        // Masque toutes les sections de contenu
        contentSections.forEach(section => {
            section.style.display = 'none';
        });

        // Affiche la section sélectionnée
        document.getElementById(targetSection).style.display = 'block';

        // Supprime la classe active de tous les liens et l'ajoute au lien cliqué
        sidebarLinks.forEach(link => link.classList.remove('active'));
        link.classList.add('active');
    });
});

