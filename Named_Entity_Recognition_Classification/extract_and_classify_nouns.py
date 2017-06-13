'''
File: extract_and_classify_nouns.py

Usage:
Provide a hardcoded filename with text that needs classification in the variable "filename".
Also, the filename can be provided as an argument by the user. Or this can be taken via a UI interface.

python extract_and_classify_nouns.py <entire file path>

This file takes a some text as the input and first extracts recognizable noun phrases from that text.
Once the noun phrases are extracted, the phrases are processed and recognized if they are named entity on Wikipedia.
If they are, the script attempts to classify them in one of four buckets: PERSON, LOCATION, ORGANIZATION or MISCELLANEOUS.

This code has dependency on the following Python libraries, and should be up to date for proper running.
User can use 'pip install <package name> -U' to make sure they're all upgraded.
PyOpenSSL, cryptography, textblob, nltk, requests

'''

# -------------------------------------------------------
# IMPORT NEEDED MODULES
# -------------------------------------------------------
from __future__ import print_function
from flask import Flask, render_template, request
from textblob import TextBlob
import time
# from py_flask import username
from functions import check_if_named_entity
from functions import classify_named_entity
start_time=time.time()
app = Flask(__name__)

# -------------------------------------------------------
# DEFINE GLOBAL VARIABLES
# -------------------------------------------------------
nouns_phrases_which_need_be_classified = []
person_nouns = set()
location_nouns = set()
organization_nouns = set()
misc_nouns = set()
username = "hello"

# -------------------------------------------------------
# Set the input filename
# -------------------------------------------------------
# If no user input.
# filename = '/Users/aditi/Downloads/dataset_small.txt'
# If user gives an input argument as filename
# if len(sys.argv) > 1:
#     if len(sys.argv) > 2:
#         sys.exit('Please provide only one filepath as argument')
#     filename = sys.argv[1]
# 
# 
# print ('Reading Dataset...')
# f = open (filename,'r')
# x = f.read()
# f.close()
# x = str(x)
#print (txt)

# -------------------------------------------------------
# This text is in ascii format and may give an error with some Python versions.
# So converting it in utf-8 format.
# -------------------------------------------------------

def process_continue(username):
    #print("hello.username = "+username)

    #txt= username.encode().decode('utf-8')
    #txt= username.decode('utf-8')
    #txt= username.encode()
    #txt = username.encode('utf-8')
    txt = username.decode('utf-8')
    print("Input text from user = "+txt)

    print ('Data Set read and decoded...')
    print ('Extracting noun phrases using TextBlob...')
    blob = TextBlob(txt)
    #print ('blob command ran')
    list_of_noun_phrases_for_frequency = (blob.noun_phrases)
    #print ('blob extract finished')


# -------------------------------------------------------
# Remove duplicates
# -------------------------------------------------------
    list_of_noun_phrases = list(set(list_of_noun_phrases_for_frequency))
    print ('Noun Phrases Extracted, total noun phrases are ',str(len(list_of_noun_phrases)))

# -------------------------------------------------------
# This is a list of all the noun phrases in the given data file
# Now need to take each noun phrase, and check if its a named entity
# -------------------------------------------------------

    print ('Checking if each noun phrase is a named entity by checking if there is a wikipedia page')

    # Trying to fix the reuse of variables
    nouns_phrases_which_need_be_classified = []


    for i in range (len(list_of_noun_phrases)):
        if i == int(len(list_of_noun_phrases)*0.1):
            print ('Done 10%...')
        if i == int(len(list_of_noun_phrases)*0.2):
            print ('Done 20%...')
        if i == int(len(list_of_noun_phrases)*0.3):
            print ('Done 30%...')
        if i == int(len(list_of_noun_phrases)*0.4):
            print ('Done 40%...') 
        if i == int(len(list_of_noun_phrases)*0.5):
            print ('Done 50%...')
        if i == int(len(list_of_noun_phrases)*0.6):
            print ('Done 60%...')
        if i == int(len(list_of_noun_phrases)*0.7):
            print ('Done 70%...')
        if i == int(len(list_of_noun_phrases)*0.8):
            print ('Done 80%...')
        if i == int(len(list_of_noun_phrases)*0.9):
            print ('Done 90%...')

        x = check_if_named_entity(list_of_noun_phrases[i])

        if x == True:
        #print ('This is a named entity')
            nouns_phrases_which_need_be_classified.append(list_of_noun_phrases[i])

# -------------------------------------------------------
# All the unique named entities from the input texts are saved now.
# Now need to check if they are named entities on Wikipedia.
# -------------------------------------------------------

    print ('All the noun phrases which are named entities extracted, total possible named entities are ',str(len(nouns_phrases_which_need_be_classified)))
            
    print ('Classifying each noun phrases into their categories...')       


    # Trying to fix the reuse of variables
    global person_nouns
    global location_nouns
    global organization_nouns
    global misc_nouns
    global frequency_of_entities
    person_nouns = set()
    location_nouns = set()
    organization_nouns = set()
    misc_nouns = set()

    for i in range (len(nouns_phrases_which_need_be_classified)):
        if i == int(len(nouns_phrases_which_need_be_classified)*0.1):
            print ('Done 10%...')
        if i == int(len(nouns_phrases_which_need_be_classified)*0.2):
            print ('Done 20%...')
        if i == int(len(nouns_phrases_which_need_be_classified)*0.3):
            print ('Done 30%...')
        if i == int(len(nouns_phrases_which_need_be_classified)*0.4):
            print ('Done 40%...')    
        if i == int(len(nouns_phrases_which_need_be_classified)*0.5):
            print ('Done 50%...')
        if i == int(len(nouns_phrases_which_need_be_classified)*0.6):
            print ('Done 60%...')
        if i == int(len(nouns_phrases_which_need_be_classified)*0.7):
            print ('Done 70%...')
        if i == int(len(nouns_phrases_which_need_be_classified)*0.8):
            print ('Done 80%...')
        if i == int(len(nouns_phrases_which_need_be_classified)*0.9):
            print ('Done 90%...') 

        x = classify_named_entity(nouns_phrases_which_need_be_classified[i])

        if x == 'Person':
            person_nouns.add(nouns_phrases_which_need_be_classified[i].title())
        elif x == 'Location':
            location_nouns.add(nouns_phrases_which_need_be_classified[i].title())
        elif x == 'Organization':
            organization_nouns.add(nouns_phrases_which_need_be_classified[i].title())
        else:
            misc_nouns.add(nouns_phrases_which_need_be_classified[i].title())
    print ('------------')
    print ('person nouns are')
    print (person_nouns)
    print ('location nouns are')
    print (location_nouns)
    print ('org nouns are')
    print (organization_nouns)
    print ('misc nouns are')
    print (misc_nouns)
    print ('------------')
    print ('The classification is complete!')
    time_taken=time.time()-start_time
    print ('Time taken in seconds - ',time_taken)
    frequency_of_entities = {entity:list_of_noun_phrases_for_frequency.count(entity) for entity in nouns_phrases_which_need_be_classified}
    print ('The frequency of the entities are -')
    print (frequency_of_entities)
    
    
@app.route('/')
def main():
    return render_template('template1.html')

@app.route('/process', methods=['POST'])
def process():
    # Retrieve the HTTP POST request parameter value from 'request.form' dictionary
    username = request.form.get('textarea')  # get(attr) returns None if attr is not present
    
    if username:
        username = username.encode('utf-8')
        process_continue(username)
        if person_nouns | location_nouns | organization_nouns | misc_nouns:
            return render_template('template2.html', personresult=person_nouns, locationresult = location_nouns, organizationresult = organization_nouns, miscresult = misc_nouns, frequency = frequency_of_entities)
        else:
            return 'No Nouns found', 400
    else:
        filedata = request.files['datafile']
        if filedata:
            process_continue(filedata.stream.read())
            if person_nouns | location_nouns | organization_nouns | misc_nouns:
                return render_template('template2.html', personresult=person_nouns, locationresult = location_nouns, organizationresult = organization_nouns, miscresult = misc_nouns, frequency = frequency_of_entities)
            else:
                return 'No Nouns found', 400
        else:
            return 'Please enter text in textfield or upload a file', 400

if __name__ == '__main__':
    app.run(debug=True)


