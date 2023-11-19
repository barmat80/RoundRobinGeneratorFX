# Round Robin Generator FX

![latest commit](https://img.shields.io/github/last-commit/barmat80/RoundRobinGeneratorFX?color=blue)
![latest release](https://img.shields.io/github/v/release/barmat80/RoundRobinGeneratorFX?color=green)

a JavaFX application that generates Round-Robin Tournament fixtures.

## How it works

* The generation logic is based on my previous project **[RoundRobinGenerator](https://github.com/barmat80/RoundRobinGenerator)**
* I tried to implement MVCI pattern by **[Dave Barrett](https://www.pragmaticcoding.ca/)**
* Themes are based on **[AtlantaFX](https://github.com/mkpaz/atlantafx)** by mkpaz

Currently, default theme is primer-light: if you want to switch to another theme you have to edit rrgfx.ini.
Available themes are:
* cupertino-dark.css
* cupertino-light.css
* dracula.css
* nord-dark.css
* nord-light.css
* primer-dark.css
* primer-light.css

## TODO List:

- [ ] Application Icon
- [ ] Theme Manager
- [ ] ProgressBar while generating fixtures
- [ ] Load a text formatted file as input
- [ ] Configurations: start date of the tournament
- [ ] Configurations: "play every X days"
- [ ] Configurations: time schedule of matchday
- [ ] Export generated Rounds as a text file
- [ ] Export generated Rounds as a pdf file

## Known Bugs

When you click one of the navigation buttons in Rounds Section for the first time, it shows twice the Matchday 1 card

## Screenshots

![best_5_fifa_world_cup_winners](https://github.com/barmat80/RoundRobinGeneratorFX/assets/10819014/63c2f2ce-72ff-4639-a69e-e8e8725df864)
*Best 5 FIFA World Cup Winners (primer-light theme)*

![atp_finals_players_2023](https://github.com/barmat80/RoundRobinGeneratorFX/assets/10819014/9277373b-8f6c-455c-8b84-7a71a58c141a)
*ATP Finals Players 2023 (cupertino-dark theme)*
