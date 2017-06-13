'''
File: functions.py

This file defines the basic functions needed for this project.

'''

# -------------------------------------------------------
# IMPORT NEEDED MODULES
# -------------------------------------------------------
from __future__ import print_function
import sys
sys.path.append('/anaconda/lib/python2.7/site-packages')
import requests
import json
import re

from classifier_strings import person_string, location_string, organization_string

# -------------------------------------------------------
# DEFINING WIKIPEDIA API URLs
# -------------------------------------------------------
wikipedia_check_entity_url = "http://en.wikipedia.org/w/api.php?action=query&prop=pageprops&format=json&titles="
wikipedia_classify_entity_url_prefix = "http://en.wikipedia.org/wiki/"
wikipedia_classify_entity_url_suffix = "?action=raw"

# -------------------------------------------------------
# DEFINING FUNCTIONS USED
# -------------------------------------------------------

def check_if_named_entity(noun):
    ''' This function checks whether a given noun phrase
    is a named entity in Wikipedia and needs to be classified.
    '''
    global wikipedia_check_entity_url

    # Process the noun phrase for Wikipedia
    noun = noun.title()
    noun = noun.replace(' ','_')
    final_url = wikipedia_check_entity_url + noun
    #print (final_url)
    resp = requests.get(final_url)
    #print (resp.text)
    api_output = str(resp.text)

    # Check if Wikipedia returns a page id for this noun phrase.
    # If it does, the noun phrase is a named entity in Wikipedia
    if 'pageid' in api_output:
        #print ('Page id exists')
        return True
    else:
        #print ('Page id does not exists')
        return False


def classify_named_entity(noun):
    ''' This function tries to get a raw wikipedia page
    for the provided input noun phrase which will then be
    classified as either PERSON, LOCATION, ORGANIZATION or MISCELLANEOUS.

    To do this, the infobox category is looked at from the
    results of the wikipedia page. Different types of infoboxes
    are collected, analyzed and stored to help with accurate classification.
    If an infobox is not readily available or the noun phrase does
    not result in a Wikipedia page directly, then it is classified as
    MISCELLANEOUS
    '''
    global wikipedia_classify_entity_url_prefix
    global wikipedia_classify_entity_url_suffix

    # Process the noun phrase for Wikipedia
    noun = noun.title()
    noun = noun.replace(' ','_')
    final_url = wikipedia_classify_entity_url_prefix + noun + wikipedia_classify_entity_url_suffix
    #print (final_url)
    resp = requests.get(final_url)
    api_output = (resp.text)

    # Process the api output
    if '#REDIRECT [[' in api_output or 'edirect [[' in api_output: # Wikipedia redirects the request to something else
        match = re.search(r'\[\[(.+)\]\]',api_output)
        newnoun = match.group(1) # Find redirected to what
        final_url = wikipedia_classify_entity_url_prefix + newnoun + wikipedia_classify_entity_url_suffix
        resp = requests.get(final_url)
        api_output = (resp.text)
    # Redirections handled

    # Need to see if there is any infobox, and see what type of infobox.
    match = re.search(r'{{Infobox (.+)',api_output)

    if not match: # No infobox present
        return 'Misc'

    else: # Infobox present
        type_of_infobox = match.group(1)
        # Examples of type of infoboxes can be person, company, country, settlement, organization etc.


        # Check if person
        match = re.search(person_string,type_of_infobox)
        if match:
            return 'Person'

        # Check if location
        match = re.search(location_string,type_of_infobox)
        if match:
            return 'Location'

        # Check if organization
        match = re.search(organization_string,type_of_infobox)
        if match:
            return 'Organization'

        # If no match
        return 'Misc'
