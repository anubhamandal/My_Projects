# Job Board

TEAM MEMBERS:
Anubha Mandal             | anubha.mandal@sjsu.edu         | 011432382
Gurnoor Singh Bhatia      | gurnoor.bhatia@sjsu.edu        | 011490284
Prudhvi Raj Mulagapati    | prudhviraj.mulagapati@sjsu.edu | 010752495
Vignesh Ramkumar          | vignesh.ramkumar@sjsu.edu      | 010724935


In this project, you will build a job board for companies to post jobs and for job seekers to search for jobs. This must be a web app hosted in the cloud. The primary language you use for server implementation _must_ be Java. You do not have to use Spring, but you need to exercise the principles, patterns, and methodologies that you have learned in the class, such as DI, AOP, MVC, ORM, and transactions. You _must_ use either a relational database, unless you choose Google App Engine or Compute Engine, in which case you can use Google Cloud Datastore if you prefer.

If any feature described below is unclear or ambiguous, and you fail to get a clear answer from the instructor or TA, you can use your best judgment to interpret it and add the missing details, provided that you clearly document and explain your reasoning in your project report.

Functional Requirements

The job board helps companies find the right employees and job seekers get the position they want. The interface must be web based, and the server needs to be hosted in the cloud, and accessible from anywhere with Internet connection.

Users and Authentication

There are two types of users, company and job seeker.

1. Company can post jobs.
2. Job seeker can search for jobs and submit applications.
3. Both job seekers and companies must use a valid email to sign up.

1.
  1. The system does not allow the same email address to be used to register for a job seeker account if it is already registered as a company account -- and vice versa.
  2. Your app must send an email to users [for both job seeker and company] with a verification code. The user needs to use that verification code to complete his account registration. A registered user cannot really use features in the system until his account is verified. A confirmation email must be sent to the user after completion of account verification.

Job Seekers

Job seekers must fill in their profile information before applying for a position.

4.  The profile contains at least the following information

1. First name
2. Last name
3. Picture **[optional]**
4. Self-introduction [optional]
5. Work experience
6. Education
7. Skills

 User can edit and update his profile at any time he likes.

5. Job seekers can search for **open** job positions

1. Search by text the user types in, which can be a job title, a company name, a skill, a mix of them, or basically, any arbitrary piece of text. A word in the text can be matched against any part of a job posting.
2. Search by filters
  1. Company name (allow multiple)
  2. Location (city names, allow multiple)
  3. Salary range (can be a single value, an open range, or a close range)

You can add more filters if you prefer.

1. Within each filter type, the relationship between the multiple values is OR; e.g., if you specify both Cisco and IBM as the company filter, the it matches a job from Cisco _or_ IBM . The different filters and free text work together with AND; i.e., all need to be matched. For example, If you specify both the company fitler and the location filter, a matching job must match both the two filters.
2. The layout of search result is up to you, but you need to support paging in case the number of matching jobs goes beyond one page, which contains up to 10 jobs.

6. Job seeker can mark/unmark any open position as interested in the search result page.

1. If a position gets filled or cancelled by the company, it would be removed from applicant&#39;s interesting list automatically. The app must send an email notification to applicant too.
2. A user can also taggle an interested job as not interested in the search result page.
3. There is no limit to the number of positions applicant can add to his interested list.
4. Your app needs to show a user&#39;s interested list, which should be presented similar to the search result page, except that there is no need to support paging in the view for interested jobs. A user needs to be able unmark/mark jobs in the interested job view too.

7. Apply for a position

1. A user can apply for a selected position in search result page or the interested jobs page.
2. A user can choose to apply with his profile, or attach a resume. In both cases, the user name and email from the user&#39;s profile becomes part of the application.
3. For any apply operation, the user would receive an email notification with the information of position(s).
4. You need to provide a view for a user to browse or cancel his applications.
  1. Each application has a status, Pending, Offered, Rejected, OfferAccepted, OfferRejcted, or Cancelled. The latter four states are also called terminal state.
  2. A user can cancel a pending application and reject an offered application.
  3. The user can cancel (or reject) one or more applications (or offers) in one transaction.
  4. The company can cancel any application that is not in a terminal state.
  5. Jobs of all states need to show up in the application view.
5. A user cannot have more than 5 pending applications.
6. A user cannot apply for the same position again if the previous application is not in a terminal state.
7. For any status change of an application, the job seeker receives an email update as well.

Companies

10. A company needs to provide basic information upon registration

1. Name
2. Website
3. Logo image URL
4. Address of headquarters
5. Description

  …

 Company can edit and update its own information at any time.

11. Company can post new positions into the system. A position contains at least the following information

1.
  1. Title
  2. Description
  3. Responsibilities
  4. Office location
  5. Salary

A company can edit and update its information mentioned above. When a job is updated, all the current applicants (applications in terminal states are not considered) are notified about the change.

12. Your app needs to provide a view for the company to browse all its positions, with a filter to restrict by the status of a position.

1. A position can be either Open, Filled, or Cancelled.
2. A company must be able to select a position to view its detail page, where the company can further make changes to the position, as well take other actions:
  1. Change the content of the position.
  2. Cancel the position unless it has an application that is in OfferAccepted state, in which case the company cannot cancel this position.
  3. Mark a position can be marked as Filled if it gets an offer accepted.
  4. When a job is filled or cancelled, all related applications in non terminal state get cancelled
  5. From the job detail page, the company needs to be able to enter a view to see all applications for this job. For each application, the company can further
    1. view the profile, or download the resume.
    2. Reject an application
    3. Accept an application by giving an offer

