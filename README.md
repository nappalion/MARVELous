Original App Design Project - README Template
===

Group members: **Robert Balatbat, Babak Shajari, Nhat Tran, Napoleon Torrico**

# MARVELous

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
"MARVELous" is a Marvel comic book reader powered by Marvel Comics API. Readers can navigate through their library, leave reviews of comics they have read, and much more. This app also features a social environment for like-minded comic book readers with the ability to follow accounts, view other readers' reviews, and even rate their reviews. Discover new comics with a built-in search feature which can filter for your favorite heroes, teams, and even villains. 

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Social Networking / Entertainment
- **Mobile:** This app is primarily developed for mobile but it is possible for use on a computer, such as https://anilist.co/. In the possibilities of a web version being created, it would have very similar features to the mobile app.
- **Story:** Allows readers to keep track of which comics they are currently reading, have read, plan to read, etc. Readers can follow others and see their reviews/library. Users can also search for new comics through a filter to find exactly what they want.
- **Market:** Any Marvel comic fan would be benefited by this app. Could be marketed towards an older audience since some comics may have sensitive material.
- **Habit:** This app could be used as much as they read Marvel comics. If they get to a certain point in an issue, they can bookmark their progress. They can also log if they completed the comic or dropped it.
- **Scope:** First, this can begin as a comic logger app. Then it can evolve into a fully social app with live discussions, posts, memes, etc. 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
- [ ] User can register for a new account, and this gets stored to a database for the user to log in later.
- [ ] Bottom toolbar allows for navigation to different fragments: Home, Search, Library, Account
- [ ] Home tab displays top rated comics and most read comics.
- [ ] Search tab displays filters which can help the user find a specific comic.
- [ ] Library tab displays tabs for users specific comics: Reading, Completed, On Hold, Dropped, Plan to Watch.
- [ ] Account tab shows user info along with favorite comics. Here the reader should be able to also see settings and logout.
- [ ] User can customize their account page with a biography, profile picture, and username.
- [ ] Users should be able to click a comic anywhere in the app and see the info of that app.
- [ ] Users should be able to favorite a comic.

**Optional Nice-to-have Stories**
- [ ] Home tab displays comics user may like (using user's info).
- [ ] Users should be able to leave reviews on comics.
- [ ] Popups can be used for a smooth experience (but fragments could be used in place of this).
- [ ] Users can like or dislike other people's reviews.
- [ ] Users can follow other users and view their pages.
- [ ] UI similar to the figma page.
- [ ] Live discussion posts.
- [ ] User can see comic variants.
- [ ] User can see the review and their page number under each comic cover thumbnail.

### 2. Screen Archetypes

* Login/Register Screen
   * User can register for a new account and login to this account.
* Home Screen
   * User can view the top rated comics and most read comics.
   * Most visual tab of the four tabs.
* Search Screen
   * User can search for the particular comic that they want to read.
   * Variety of search filters.
* Library Screen
   * Reader can see their specific comics: Reading, Completed, On Hold, Dropped, Plan to Watch.
* Account Screen
   * User can access settings and logout.
   * Shows user profile picture, biography, favorite comics, etc.

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home
* Search
* Library
* Account

**Flow Navigation** (Screen to Screen)

* Login Screen
   * Home Screen
* Home Screen
   * Review Screen
      * Review Page
      * Comic Cover Variant Screen
* Search Screen
   * Filter Screen  
   * Review Screen
      * Review Page
      * Comic Cover Variant Screen
* Library Screen
   * Review Screen
      * Review Page
      * Comic Cover Variant Screen
* Account Screen
   * Settings Screen
   * Review Screen
      * Review Page
      * Comic Cover Variant Screen 

## Wireframes
<img src="Wireframe.PNG" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
