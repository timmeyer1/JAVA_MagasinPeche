// ----------------------------------- PAGE PROFIL -----------------------------------

// Récupère tous les liens de la barre latérale
const sidebarLinks = document.querySelectorAll('.profil-sidebar a');
// Récupère toutes les sections de contenu
const contentSections = document.querySelectorAll('.content-section');

// Fonction pour afficher la section en fonction de son identifiant
function showSection(targetSection) {
    // Masque toutes les sections de contenu
    contentSections.forEach(section => {
        section.style.display = 'none';
    });

    // Affiche la section sélectionnée
    const targetElement = document.getElementById(targetSection);
    if (targetElement) {
        targetElement.style.display = 'block';
    }

    // Supprime la classe active de tous les liens et l'ajoute au lien cliqué
    sidebarLinks.forEach(link => link.classList.remove('active'));
    const activeLink = document.querySelector(`.profil-sidebar a[data-section="${targetSection}"]`);
    if (activeLink) {
        activeLink.classList.add('active');
    }
}

// Ajoute un écouteur d'événements pour chaque lien de la barre latérale
sidebarLinks.forEach(link => {
    link.addEventListener('click', function (event) {
        event.preventDefault();

        const targetSection = link.getAttribute('data-section');

        // Affiche la section sélectionnée
        showSection(targetSection);

        // Modifie l'URL en ajoutant un paramètre vide '' correspondant à la section active
        const newUrl = `${window.location.pathname}?=${targetSection}`;
        history.pushState({ section: targetSection }, '', newUrl); // Change l'URL sans recharger la page
    });
});

// Gère le chargement de la page avec un paramètre d'URL
window.addEventListener('load', function () {
    // Récupère le paramètre vide '' de l'URL
    const params = new URLSearchParams(window.location.search);
    const sectionFromUrl = params.get('');

    // Si un paramètre 'section' est trouvé dans l'URL, sélectionne la section correspondante
    if (sectionFromUrl) {
        showSection(sectionFromUrl); // Affiche la section en fonction du paramètre 'section'
    } else {
        // Si aucun paramètre 'section' n'est précisé, affiche la première section par défaut
        showSection('infos');
    }
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
    document.getElementById('profileForm').submit();
}