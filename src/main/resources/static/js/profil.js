// ----------------------------------- PAGE PROFIL -----------------------------------

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

// ----------------------------------- PAGE MODIF -----------------------------------

let isEditMode = false;

function toggleEditMode() {
    // Inverse le mode d'édition
    isEditMode = !isEditMode;

    // Affiche les champs de formulaire et masque les textes si on est en mode édition
    document.querySelectorAll('.info-text').forEach(element => {
        element.style.display = isEditMode ? 'none' : 'block';
    });
    document.querySelectorAll('.info-input').forEach(element => {
        element.style.display = isEditMode ? 'block' : 'none';
    });

    // Affiche ou masque le bouton d'enregistrement
    document.getElementById('saveButton').style.display = isEditMode ? 'inline-block' : 'none';

    // Change le texte du bouton de bascule
    document.querySelector("button[onclick='toggleEditMode()']").textContent = isEditMode ? "Annuler" : "Modifier le profil";
}

function saveProfile() {
    // Soumet le formulaire avec les nouvelles valeurs
    // Vous pouvez ajouter ici une logique de validation avant soumission
    document.getElementById('profileForm').submit();
}