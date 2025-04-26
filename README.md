# MindMap CLI

Un outil en ligne de commande pour créer et gérer des cartes mentales directement depuis votre terminal.

## Technologies utilisées

- Java 21
- Maven (gestionnaire de dépendances et de projet)

## Installation

Avant de commencer, assurez-vous d'avoir installé :

- [Java JDK 21](https://adoptium.net/)
- [Apache Maven](https://maven.apache.org/)

Vérifiez que Java et Maven sont bien installés :

```bash
java -version
mvn -version
```
Clonez le projet :

```bash
git clone https://github.com/TheoPilet/mindmap-cli.git
cd mindmap-cli
```
Installez les dépendances et compilez :

```bash
mvn clean install
```
## Lancer l'application

Compiler puis exécuter manuellement :

```bash
mvn package
java -cp target/mindmap-cli-1.0-SNAPSHOT.jar com.mindmap.App
```

Executer la batterie de test :

```bash
mvn test
```

## Commandes
| Commande                     | Description                                                                 |
|------------------------------|-----------------------------------------------------------------------------|
| `ajouter <nom>, <parent>`     | Crée un nouveau nœud avec le nom `<nom>` sous le nœud parent `<parent>`.    |
| `renommer <ancien>, <nouveau>`| Renomme le nœud `<ancien>` en `<nouveau>`.                                  |
| `supprimer <nom>`             | Supprime le nœud spécifié par `<nom>`.                                      |
| `trouver <nom>`               | Trouve et affiche le chemin vers le nœud spécifié par `<nom>`.              |
| `afficher`                    | Affiche l'ensemble des nœuds de la carte mentale.                           |
| `enregistrer <fichier.json>`  | Sauvegarde la carte mentale dans le fichier `<fichier.json>`.               |
| `charger <fichier.json>`      | Charge la carte mentale à partir du fichier `<fichier.json>`.               |
| `nouvelle <nom>`              | Crée une nouvelle carte mentale avec le nom `<nom>`.                        |
| `quitter`                     | Quitte l'application.                                                      |

## Exemple d’utilisation

```bash
Bienvenue dans MindMap CLI
Nom de la carte mentale : finance     

Commandes :
  ajouter <nom>, <parent>
  renomer <ancien>, <nouveau>
  supprimer <nom>
  trouver <nom>
  afficher
  enregistrer <fichier.json>
  charger <fichier.json>
  nouvelle <nom>
  quitter
> ajouter intérêt composé, finance
Nœud ajouté.

Commandes :
  ajouter <nom>, <parent>
  renomer <ancien>, <nouveau>
  supprimer <nom>
  trouver <nom>
  afficher
  enregistrer <fichier.json>
  charger <fichier.json>
  nouvelle <nom>
  quitter
> ajouter action, finance
Nœud ajouté.

Commandes :
  ajouter <nom>, <parent>
  renomer <ancien>, <nouveau>
  supprimer <nom>
  trouver <nom>
  afficher
  enregistrer <fichier.json>
  charger <fichier.json>
  nouvelle <nom>
  quitter
> renomer action, obligation
Nœud renommé.

Commandes :
  ajouter <nom>, <parent>
  renomer <ancien>, <nouveau>
  supprimer <nom>
  trouver <nom>
  afficher
  enregistrer <fichier.json>
  charger <fichier.json>
  nouvelle <nom>
  quitter
> supprimer obligation
Nœud supprimé.

Commandes :
  ajouter <nom>, <parent>
  renomer <ancien>, <nouveau>
  supprimer <nom>
  trouver <nom>
  afficher
  enregistrer <fichier.json>
  charger <fichier.json>
  nouvelle <nom>
  quitter
> trouver intérêt composé
Chemin : /finance/intérêt composé

Commandes :
  ajouter <nom>, <parent>
  renomer <ancien>, <nouveau>
  supprimer <nom>
  trouver <nom>
  afficher
  enregistrer <fichier.json>
  charger <fichier.json>
  nouvelle <nom>
  quitter
> afficher
- finance
  - intérêt composé

Commandes :
  ajouter <nom>, <parent>
  renomer <ancien>, <nouveau>
  supprimer <nom>
  trouver <nom>
  afficher
  enregistrer <fichier.json>
  charger <fichier.json>
  nouvelle <nom>
  quitter
> enregistrer finance.json
Carte sauvegardée dans : data/finance.json
Carte enregistrée.

Commandes :
  ajouter <nom>, <parent>
  renomer <ancien>, <nouveau>
  supprimer <nom>
  trouver <nom>
  afficher
  enregistrer <fichier.json>
  charger <fichier.json>
  nouvelle <nom>
  quitter
> nouvelle droit
Cela va remplacer la carte mentale actuelle et les modifications non enregistrées seront perdues.
Voulez-vous continuer ? (oui/non) : oui
Nouvelle carte créée !

Commandes :
  ajouter <nom>, <parent>
  renomer <ancien>, <nouveau>
  supprimer <nom>
  trouver <nom>
  afficher
  enregistrer <fichier.json>
  charger <fichier.json>
  nouvelle <nom>
  quitter
> charger finance.json
Cela va remplacer la carte mentale actuelle et les modifications non enregistrées seront perdues.
Voulez-vous continuer ? (oui/non) : oui
Carte chargée avec succès !

Commandes :
  ajouter <nom>, <parent>
  renomer <ancien>, <nouveau>
  supprimer <nom>
  trouver <nom>
  afficher
  enregistrer <fichier.json>
  charger <fichier.json>
  nouvelle <nom>
  quitter
> quitter
```