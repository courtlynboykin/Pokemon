# About
This Pokémon Card Game App is a digital, simplified card game built using the Pokémon API. Over the coming weeks, we will incrementally develop this app, adding new features as we learn. This project aims to provide a hands-on learning experience, allowing for experimentation with various components, colors, typography, interactions, and responsiveness.

## Features
- View and Search Pokémon Cards: Browse through a collection of Pokémon cards and use the search functionality to find specific Pokémon.
- View and Compare Stats: Compare the stats of different Pokémon to understand their strengths and weaknesses.
- Add Pokémon Cards to a Deck: Build your custom deck by adding your favorite Pokémon cards.
- Add Pokémon to Squad: Select up to 6 Pokémon to be in your squad by clicking a button on the Pokémon’s card.
- Squad Management: Ensure duplicate Pokémon cannot be added to the squad. Users can add and remove Pokémon from their squad.
- Battle Button: When a user has at least 2 Pokémon selected for their squad, the “Battle” button becomes available to click (future feature to join a queue and battle other users).
- State Management: Maintain the state of the squad, enabling and disabling the "Battle" button based on the number of Pokémon in the squad.

## Design and API Usage
- Pokémon API: To fetch the original 150 Pokémon, the app uses the PokeAPI endpoint: https://pokeapi.co/api/v2/pokemon?limit=151.

### Installation
1. Clone the repository: ```git clone https://github.com/courtlyncodes/Pokemon.git```
2. Open in Android Studio: Launch Android Studio and open the cloned project.
3. Build and Run: Build and run the project on your emulator or device.

### Usage
1. Browse and search for Pokémon cards from the main screen.
2. Add Pokémon to your squad by clicking the "Add to Squad" button on the Pokémon’s card. You can add up to 6 Pokémon to your squad.
3. Manage your squad: Add and remove Pokémon from your squad as needed. Ensure no duplicate Pokémon are added.
4. Battle button: Once you have at least 2 Pokémon in your squad, the “Battle” button will be enabled. (Note: The battle feature will be implemented in future updates).
