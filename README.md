![](https://repo.dementisimus.dev/dev/dementisimus/mrs/MapRatingSystem/images/mrs_logo.png)

### *an easy && lightweight Plugin / and or API // for letting your players rate your maps!*
------------

## some impressions first

![](https://repo.dementisimus.dev/dev/dementisimus/mrs/MapRatingSystem/images/mrs_menu.png)

![](https://repo.dementisimus.dev/dev/dementisimus/mrs/MapRatingSystem/images/mrs_menu_de.png)

![](https://repo.dementisimus.dev/dev/dementisimus/mrs/MapRatingSystem/images/mrs_ratings.png)

![](https://repo.dementisimus.dev/dev/dementisimus/mrs/MapRatingSystem/images/mrs_ratings_selected.png)

![](https://repo.dementisimus.dev/dev/dementisimus/mrs/MapRatingSystem/images/server.png)

## Features

- **easy** installation && setup via **console** on server startup
- possibility to **choose** between **MongoDB**, **MariaDB** or **SQLite**
- **automatic** dependency **installer** - **no need** to download dependencies **by yourself**!
- async
- lightweight
- **caches** used data for **maximum performance**
- **multilingual** - by selecting the **correct language** for a player **automatically**
- high scalability
- a server (dementisimus.dev) where you can **test it out**
- **docs** on [docs.dementisimus.dev](https://docs.dementisimus.dev/MapRatingSystem/ "docs.dementisimus.dev")
- **Support** @ [Discord](https://discord.gg/sTRg8A7 "Discord") && [SpigotMC](https://www.spigotmc.org/conversations/add?to=dementisimus "SpigotMC") (Discord **preferred, so your question might help other people as well!**)
- **issues** can be **reported** [here](https://discord.gg/sTRg8A7 "here")
- [have a look at upcoming features on GitHub!](https://github.com/dementisimus/MapRatingSystem/projects "have a look at upcoming features on GitHub!")

## API-Usage

```xml
<!-- dementisimus.dev-Repository -->
<repository>
     <id>dementisimus.dev</id>
     <url>https://repo.dementisimus.dev</url>
</repository>

<!-- MapRatingSystem-1.2.0 by dementisimus -->
<dependency>
     <groupId>dev.dementisimus.mrs</groupId>
     <artifactId>MapRatingSystem</artifactId>
     <version>1.2.0</version>
     <scope>provided</scope>
</dependency>
```

```yaml
#this you need to specify in your plugin.yml!
loadbefore: [MapRatingSystem]
```

```java
// (optional!)
// • initialize a listener for the RateMapEvent (https://docs.dementisimus.dev/MapRatingSystem/dev/dementisimus/mrs/api/event/RateMapEvent.html)
@EventHandler
public void onRateMap(RateMapEvent rateMapEvent) {
       //your code here
}
```

```java
[example]
// • initialize (this must be done in your onEnable-Method!) your Map by creating an instance of dev.dementisimus.mrs.api.MapRating
// • add RatingTypes your players'll be able to vote for
// • initialize the slots for your chosen RatingTypes
// • set the slot on which the rate-map-item will be placed on
// • set the material which will be used for the rate-map-item
new MapRating("Amu", new RatingType[]{RatingType.TERRIBLE, RatingType.BAD, RatingType.OKAY, RatingType.GOOD, RatingType.AMAZING}, new Integer[]{0, 2, 4, 6, 8}, 4, Material.MAP /*or any other Item//RatingType, etc*/);
```

## » more information: ([click](https://docs.dementisimus.dev/MapRatingSystem/dev/dementisimus/mrs/api/MapRating.html "click"))

## ToS
- the plugin **may not** be decompiled, modified, sold, be published as your own.
- the plugin **may** be used in public/private plugins; credits would be nice!

# !!! read this carefully before downloading !!!
> **- in order to use this plugin properly, you need to use Java >11 & [Paper by PaperMC](https://papermc.io/downloads "Paper by PaperMC") >1.16! (Paper is an extension/fork of Spigot by SpigotMC, which implements many useful features!)**

## installation
- **[If your server is currently running, make sure you stop him, do NOT reload your server!]**
- **put this plugin** into the **plugin folder** of **your** **server**.
- you can now **start** your **server**!

## install an update
- **download** the **new version** and put it in your **plugin-folder**. **Follow** the **instructions** on the update post, **if given**.
- **restart** or **start** [do **NOT** reload] your **server**.

# » Have fun!
