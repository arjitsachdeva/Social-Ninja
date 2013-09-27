Social-Ninja
============

This Android application consists of a search page, where a user can type in any search query, and the results are fetched from Facebook and Twitter, via Facebook and Twitter's public search APIs. 

The limit has been set to 10 results from Facebook and Twitter each. Facebook posts are displayed in a swiping page layout fashion, while Twitter results are in a scrolling webview. 

The Graph API returns various parameters for a search on Facebook, like 'Post ID', 'Description', 'Message', 'Picture URL' etc. I pulled up just the Description(in case of a shared link), and/or status update(in case of a POST), and the Picture is displayed via the Picture URL(if available). 

A corresponding Wikipedia page is also accessible via the options menu(if exists).

The search terms are stored on a MySQL server via PHP and the user's search history is locally stored in SQLite as well. 

This application provides a starting point for anyone interested in getting started with a social API(like Facebook), and displaying the results in a Fragment Pager. A simple Android-PHP communication is also implemented as stated above.
