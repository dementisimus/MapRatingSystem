# MapRatingSystem [![main build status](https://github.com/dementisimus/MapRatingSystem/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/dementisimus/MapRatingSystem) [![develop build status](https://github.com/dementisimus/MapRatingSystem/actions/workflows/build.yml/badge.svg?branch=develop)](https://github.com/dementisimus/MapRatingSystem/tree/develop)
## _Because feedback is important._

MapRatingSystem is a **lightweight**, **inventory based** **feedback** (**rating**) **system** for **minecraft maps**.

## **Features**

- **allow** your **users** to **rate** your **maps**
- **view** user **feedback** on **maps** in an inventory (`/maprating`)
- **Multilingual** console messages (languages currently available: **English**, **German**)
- Each **player may choose** a suitable language via `/language`, otherwise the language will be **picked automatically**
- **No need** to **install anything** by yourself - **MapRatingSystem** does **everything** for you (except for **MapRatingSystem itself** ¬‿¬ )!
- **Powerful API** for **Java Developers**
- **Continuous development** with many **[planned features]**
- **24/7-Support** at our **[Discord-Server]**

## **Requirements**

1. **Java 16**
2. **Spigot 1.17.1**
3. **Access** to the **server console** (for the **automated setup**)
4. **Access** to a **database** **(MongoDB, MariaDB (MySQL), SQLite)**

## **Installation**

1. Make sure your **server** is **stopped**, or will be **restarted**. Do **not reload** your server!
2. **Download** the **latest version** of _**CoreAPI**_ & _**MapRatingSystem**_ from **[GitHub Releases]**
3. **Move** the **downloaded jar-file** to the **`plugins`-folder**
4. **Start** (or **restart**) your **server**
5. **Go through** the **installation process** (**setu**p) in your **console** by **answering** the **prompted questions** with **commands** (**commands** represent the **data**, may be **infinitely long**)

## **Development**

```java
//Docs: https://docs.dementisimus.dev/development/MapRatingSystem/1.3.0/
MapRating mapRating = new CustomMapRating();

//optional
mapRating.setMapRatingItemMaterial(Material.DIAMOND);
mapRating.setMapRatingItemSlot(8);

//gives the rate map item to a player
mapRating.setRateMapItem(player);

//disables the rate map item on player join
mapRating.doNotSetRateMapItemOnPlayerJoin();
```
```java
@EventHandler
public void on(PlayerRateMapEvent event) {
    //Docs at https://docs.dementisimus.dev/development/MapRatingSystem/1.3.0/dev/dementisimus/mrs/api/events/PlayerRateMapEvent.html
        
    //to disable the default messages & sounds of the event
    event.setCancelled(true);
}
```

## **Images**

<p align="center">
  <img src="https://dementisimus.dev/img/MapRatingSystem/rate_map.jpg" />
   <br>
  <img src="https://dementisimus.dev/img/MapRatingSystem/map_ratings.jpg" />
</p>

## **Special thanks to**

- [@TearingBooch482] for **helping** me **stress-testing** every **new version** of my **plugins**

## **License**

» [Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License]

## find us on

[<img src="https://discordapp.com/assets/e4923594e694a21542a489471ecffa50.svg" alt="" height="55" />](https://discord.gg/sTRg8A7)

# **Happy map rating!**

[planned features]: <https://github.com/dementisimus/MapRatingSystem/projects/2>
[Discord-Server]: <https://discord.gg/sTRg8A7>

[GitHub Releases]: <https://github.com/dementisimus/MapRatingSystem/releases>

[Advanced-Slime-World-Manager]: <https://github.com/Paul19988/Advanced-Slime-World-Manager>
[@TearingBooch482]: <https://github.com/TearingBooch482>

[Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License]: <https://creativecommons.org/licenses/by-nc-nd/4.0/>
