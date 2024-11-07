// ------------ Filtrer les produits par catégorie ------------
document.addEventListener('DOMContentLoaded', function() {
    // Sélectionner toutes les icônes
    const icons = document.querySelectorAll('.category-icons .icon');
    const products = document.querySelectorAll('.produit-box');

    // Fonction pour filtrer les produits par catégorie
    function filterProductsByCategory(category) {
        products.forEach(product => {
            const productCategory = product.getAttribute('data-category');  // La catégorie du produit
            if (category === 'all' || productCategory === category) {
                product.style.display = '';  // Afficher le produit
            } else {
                product.style.display = 'none';  // Masquer le produit
            }
        });
    }

    // Ajouter un événement au clic sur chaque icône
    icons.forEach(icon => {
        icon.addEventListener('click', function() {
            // Retirer la classe active de toutes les icônes
            icons.forEach(i => i.classList.remove('active'));

            // Ajouter la classe active à l'icône cliquée
            icon.classList.add('active');

            const category = icon.getAttribute('data-category');
            filterProductsByCategory(category);
        });
    });

    // Option de réinitialisation (montrer tous les produits)
    document.querySelector('.search-button').addEventListener('click', function() {
        filterProductsByCategory('all');
        // Retirer la classe active de toutes les icônes après réinitialisation
        icons.forEach(i => i.classList.remove('active'));
    });
});


// ------------ Filtrer les produits par nom ------------
document.addEventListener('DOMContentLoaded', function() {
    // Sélectionner les éléments nécessaires
    const searchInput = document.getElementById('searchInput');
    const products = document.querySelectorAll('.produit-box');

    // Fonction pour filtrer les produits par nom
    function filterProductsByName(query) {
        products.forEach(product => {
            const productName = product.querySelector('.produit-details h2').innerText.toLowerCase();
            if (productName.includes(query.toLowerCase())) {
                product.style.display = '';  // Afficher le produit si le nom contient la requête
            } else {
                product.style.display = 'none';  // Masquer le produit si le nom ne correspond pas
            }
        });
    }

    // Ajouter un événement sur l'input de recherche
    searchInput.addEventListener('input', function() {
        const query = searchInput.value;
        filterProductsByName(query);
    });

    // Option de réinitialisation (montrer tous les produits)
    document.querySelector('.search-button').addEventListener('click', function() {
        searchInput.value = '';  // Réinitialiser la barre de recherche
        filterProductsByName('');  // Montrer tous les produits
    });
});