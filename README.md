# CMPUT 301: Lab 2 Participation Exercise - ListyCity

## Student Details
- **Full Name:** Aarush Bhat
- **CCID:** Aarush

## References and Resources
- [CMPUT 301 Lab 2 instructions page](https://ualberta-cmput301.github.io/labs/lab2_instructions.html)
- Lab 2 slide decks (linked from the instructions page above): *OOP Principles*, *Android Basics*, and the *ListyCity Instructions* walkthrough
- [Jetpack Compose overview - Android Developers](https://developer.android.com/develop/ui/compose)
- [The Activity lifecycle - Android Developers](https://developer.android.com/guide/components/activities/activity-lifecycle)
- Be my eyes' [BeMyAI](https://www.bemyeyes.com/bme-ai/) feature to describe screenshots.
- [Web Content Accessibility Guidelines (WCAG 2, level AA)](https://www.w3.org/WAI/WCAG2AA-Conformance) for best A11y principles.
 - Just because one has accessibility needs does not mean they know everything; it would be foolish to assume so. Accessibility is vast, and it is  a spectrum.

## Verbal Collaboration
List students' names and CCIDs here, or simply put `N/A` if not applicable.
N/A

## Implementation Notes
ListyCity`` follows the walkthrough's `CityRepository` / `CityListScreen` / `CityRow` structure, then extends it for the participation exercise ("allow for the addition
of new cities as well as the deletion of existing ones"):

- Add a city: the "ADD CITY" action in the top bar reveals a text field and
  a "CONFIRM" button (matching the reference screenshots); confirming a
  non-blank name calls `CityRepository.addCity`.
- Delete a city: tapping a row selects it (tap again to deselect); the
  "DELETE CITY" action is only enabled while a city is selected, and calls
  `CityRepository.removeCity`.
- Accessibility (no way for me to use the app without it): row selection uses `Modifier.selectable` inside a
 `selectableGroup()` (not just a background colour)
 - This is done so screen readers such as TalkBack announce each city's selected state
 - The "DELETE CITY" button exposes its disabled state instead of failing silently
 - Text uses `sp`units throughout so it respects the system font-size setting, and the app bar colour was chosen to keep white text/icons above a 4.5:1 contrast ratio which can be better than the default lighter Material teal.
