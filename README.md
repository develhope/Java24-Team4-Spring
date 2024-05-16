# java course spring base app
Base app used as base template for Java Course Spring project. 
The project will be provided without any type of dependency, and configured with:
Language: Java<br />
Spring boot: 3.2.1<br />
Packaging: Jar<br />
Java: 21<br />

# Entities: 
- User -> - Advertiser (modificare Agency)                                   
          - Artist
          - Listner
          - Administration
  (Il ruolo di User sarà gestito tramite Enum o tramite Classi figlie)?

- Song
- Genre
- Album
- Comment
- Like_Songs
- PlayList
- Advertistment
- Subscription

# Controllers
- SongController -> - GET:  - FindAllSongs
                            - FindSongsById
                            - FindSongsByGenre
                            - FindsongsByArtist
                            --- ? 
                    - POST: - addSong (request body)
                    - PUT: - updateSong (request body)
                    - DELETE: - deleteSongById (request path Variable)
  
- UserController -> - GET:
                    - POST:
                    - PUT:
                    - DELETE:
- ?Controller
