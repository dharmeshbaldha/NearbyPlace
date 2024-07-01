Near-By Place

**Authorization Key**
  - A class name with Constants where const `AUTHORIZATION_KEY` is declared. where the API key needs to be replaced to fetch nearby locations from Foursquare API.

**Basic Documentation**
DESIGN
  - Implemented design in the simplest possible way with minimal views has been used.

DECISION
  - Decide to use MVVM architecture for a better approach to clean architecture
  - Used Coroutines to manage better thread-safe things.
  - Navigation framework to use easy and efficient navigation.
  - Another use of Navigation Framework is ActionBar is synced with NavigationUI utils, hence the title, click and other actions can be implemented centralized in a specific manner.
  - HILT for easy-to-create and use dependency.
  - Constraint Layout is also used for making complex screens render efficiently with less memory required.

TRADE-OFF
  - Have used the Glide library to load images to ImageView asynchronously.

THINGS I'VE USED 
  - MVVM to achieve clean architecture
  - Coroutine for thread management 
  - Live Data
  - Navigation Framework
  - HILT for dependency injection
  - Google Play Service for Location
  - Retrofit for Network Call
  - View Binding
  - Constraint Layout
  - Actionbar Sync with Navigation Framework via NavigationUI

THINGS CAN BE ADDED FOR BETTER APPROACH
  - Progress Loader while API call can be used through a third-party library which can be more efficient in almost all ways. (performance, minimum & accurate code, etc..)
  - Also Test Cases can be added but due to not having experience with Coroutine to write unit test cases and also having a limited time, not able to implement test cases.
  - Overall design can be improved by adding colors & views to look more attractive.
  - I have used ActionBar instead of ToolBar, but any of them can be used based on the final flow of the App. (though ToolBar is a better approach)
