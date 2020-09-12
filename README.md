# Hello Daresay!

I enjoyed working with this assignment. The eight hour time-limit got me though, so no creative awesomeness or bonus features. Just the basic requirements with an unpolished (but nice and standard) UI.

My API key secret is not committed to the repository. Remember to add yours to the `LiveClient` when testing. Also, as a hint, Xcode will show a warning about it.

Things implemented / prioritized:
- Made using SwiftUI and Combine.
- Support for light and dark mode.
- Dynamic type (accessibility).
- Error handling is in place, but no UI for it yet.

The next step if, I were to continue this app, would be to introduce a higher-order `Client` that wraps / maps other `Clients` and lets you transform and add side effects to them. Then you can simulate a flaky internet by adding delays and random errors to the `LiveClient`, which you otherwise can't since you don't have control over that endpoint. Then, adding spinners, pagination, error handling UI and testing edge cases would be nice and fun. Some more things to consider:

- A `MockClient` for offline development and testing.
- An option to choose between rows and grid in `MovieListView`.
- Pagination for the movie list (not just one page as it is now).
- Spinner when loading.
- UI for error handling when it can't be avoided by design.
- A more interesting and detailed `DetailView`. 
- ...

Well, I'm looking forward to discuss this assignment with you!

Cheers,

Gustaf Jorlin

---

