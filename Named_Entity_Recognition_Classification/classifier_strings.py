'''
File: classifier_strings.py

This file defines the different types of infoboxes that are returned by Wikipedia
If an infobox type is not added here properly, it may lead to noun phrase being mis-classified.

If such a infobox type is found, it can be added to the strings here.

Usage:

This is the format in which Wikipedia returns the output where infobox can be seen.
Example:
{{Coord|51|N|9|E|display=title}}
{{Infobox country
|conventional_lon

the talk page before editing it.
 ----->{{Infobox person
<!----
   Note:

etc.

This 'country', 'person' etc. can be extracted and added to this file in the variables below


Author: Aditi Garg
'''

person_string = r'[Pp]erson|[Cc]haracter|[Oo]fficeholder|[Pp]resident|[Aa]rtist|[Pp]olitician|[Ww]riter|[Bb]iography|[Cc]ricket|[Gg]olf'
location_string = r'[Cc]ontinent|[Cc]ountry|[Ss]ettlement|[Ss]tate|[Ss]treet|[Pp]lace'
organization_string = r'[Oo]rganization|[Uu]nited|[Aa]gency|[Cc]ompany|[Ff]action|[Dd]epartment|[Mm]agazine|[Uu]niversity|[Ss]chool|[Tt]eam'

