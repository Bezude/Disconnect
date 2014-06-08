/*
 *  Main.java
 *  ECS 163 Final
 *  Alan Tai and Benjamin Roye
 *
 */

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;
import java.util.Arrays;

public class Main {

    private static int instnm = 0;
    private static int address = 1;
    private static int city = 2;
    private static int stateabr = 3;
    private static int zipcode = 4;
    private static int telephone = 5;
    private static int opeflag = 6; //indicates the institution's degree of eligibility for Title IV aid.
    private static int webaddress = 7;
    private static int iclevel = 8; //indicates if an institution is 4 year, 2 year, or less than 2 year
    private static int control = 9; //indicates if institution is public, private not-for-profit, or private
    private static int hloffer = 10;
    /*
     "Highest level of offering (generated, based on response to IC
     survey)
     0 - Other
     1 - Postsecondary award, certificate or diploma of less than one academic year
     2 - Postsecondary award, certificate or diploma of at least one but less than two academic years
     3 - Associate's degree
     4 - Postsecondary award, certificate or diploma of at least two but less than four academic years
     5 - Bachelor's degree
     6 - Postbaccalaureate certificate
     7 - Master's degree
     8 - Post-master's certificate
     9 - Doctor's degree
     b - None of the above or no answer
     -2 - Not applicable, first-professional only
     -3 - Not Available"
     */
    private static int hbcu = 11; //indicates whether institution is a Historically Black College or not
    private static int hospital = 12; //indicates whether college has a hospital or not
    private static int medical = 13; //indicates whether institution has a medical degree (medicine, dentistry, osteopathic medicine, veterinary medicine)
    private static int locale = 14;
    /*
     "Locale codes identify the geographic status of a school on an urban continuum ranging from “large city” to “rural.”  They are based on a school’s physical address. The urban-centric locale codes introduced in this file are assigned through a methodology developed by the U.S. Census Bureau’s Population Division in 2005.  The urban-centric locale codes apply current geographic concepts to the original NCES locale codes used on IPEDS files through 2004. 

     11 = City: Large: Territory inside an urbanized area and inside a principal city with population of 250,000 or more. 

     12 = City: Midsize: Territory inside an urbanized area and inside a principal city with population less than 250,000 and greater than or equal to 100,000.

     13 = City: Small: Territory inside an urbanized area and inside a principal city with population less than 100,000.

     21 = Suburb: Large: Territory outside a principal city and inside an urbanized area with population of 250,000 or more.

     22 = Suburb: Midsize: Territory outside a principal city and inside an urbanized area with population less than 250,000 and greater than or equal to 100,000.

     23 = Suburb: Small: Territory outside a principal city and inside an urbanized area with population less than 100,000.

     31 = Town: Fringe: Territory inside an urban cluster that is less than or equal to 10 miles from an urbanized area.

     32 = Town: Distant: Territory inside an urban cluster that is more than 10 miles and less than or equal to 35 miles from an urbanized area.

     33 = Town: Remote: Territory inside an urban cluster that is more than 35 miles of an urbanized area.

     41 - Rural: Fringe: Census-defined rural territory that is less than or equal to 5 miles from an urbanized area, as well as rural territory that is less than or equal to 2.5 miles from an urban cluster. 

     42 = Rural: Distant: Census-defined rural territory that is more than 5 miles but less than or equal to 25 miles from an urbanized area, as well as rural territory that is more than 2.5 miles but less than or equal to 10 miles from an urban cluster. 

     43 = Rural: Remote: Census-defined rural territory that is more than 25 miles from an urbanized area and is also more than 10 miles from an urban cluster.
     */
    private static int openpublic = 15;
    private static int closedat = 16;
    private static int ialias = 17;
    private static int category = 18;
    /*
     "Institutional category was derived using the level of offerings reported on the Institutional Characteristics (IC) component and the number and level of awards that were reported on the Completions (C) component.

     Category descriptions:

     1) Degree-granting, graduate with no undergraduate degrees - These institutions offer a Master's degree, Doctor's degree  or a First-professional degree and do not offer a Bachelor's degree or an Associate's degree. 

     2) Degree-granting, primarily baccalaureate or above - These institutions offer a Bachelor's degree, Master's degree,Doctor's degree or a First-professional degree.  Also, the total number of degrees/certificates at or above the bachelor's level awarded divided by the total number of degrees/certificates awarded is greater than 50 percent. 

     3) Degree-granting, not primarily baccalaureate or above - These institutions offer a Bachelor's degree, Master's degree, Doctor's degree,or a First-professional degree.  Also, the total number of degrees/certificates at or above the bachelor's level awarded divided by the total number of degrees/certificates awarded must be less than or equal to 50 percent.

     4) Degree-granting, Associate's and certificates - Institutions offer an Associate's degree and may offer other postsecondary certificates, awards or diplomas of less than one academic year; at least one but less-than two academic years; at least two but less-than four academic years. This category also includes institutions that offer a postbaccalaureate certificate, Post-master's certificate or a First-professional certificate and the highest degree offered is an Associate's degree.

     5) Nondegree-granting, above the baccalaureate - Institutions do not offer Associate's, Bachelor's, Master's, Doctor's or First-professional degrees, but offer either Postbaccaulaureate, Post-master's or First-professional certificates. 

     6) Nondegree-granting, sub-baccalaureate - Institutions do not offer Associate's, Bachelor's , Master's, Doctor's, or First-professional degrees, or certificates above the baccalaureate level. They do offer postsecondary certificates, awards or diplomas of less than one academic year; at least one but less than two academic years; or at least two but less than four academic years.

     Technical details for Institutional Category (INSTCAT):

     Total degrees and certificates and total bachelor's degrees and all other degrees/certificates above the bachelor's degree awarded are derived using data from the Completions component.

     Total bachelor's degrees and all other degrees/certificates above the bachelor's degree is the sum of: 

     Bachelor's degrees (CTOTALT,AWLEVEL=5,MAJORNUM=1)
     Postbaccalaureate certificates (CTOTALT,AWLEVEL=6,MAJORNUM=1)
     Master's degrees (CTOTALT,AWLEVEL=7,MAJORNUM=1)
     Post-master's certificates (CTOTALT,AWLEVEL=8,MAJORNUM=1)
     Doctor's degrees (CTOTALT,AWLEVEL=9,MAJORNUM=1)
     First-professional degrees (CTOTALT,AWLEVEL=10,MAJORNUM=1)
     First-professional certificates (CTOTALT,AWLEVEL=11,MAJORNUM=1)

     Total degrees and certificates is the sum of Bachelor degrees and all other degrees/certificates above the bachelors degree as defined above added to the following:

     Associate's degrees (CTOTALT,AWLEVEL=3,MAJORNUM=1)
     Postsecondary certificates, awards or diplomas of at least two but less-than four academic years 
     (CTOTALT,AWLEVEL=4, MAJORNUM=1).
     Postsecondary certificates, awards or diplomas of at least one but less-than two academic years
     (CTOTALT,AWLEVEL=2,MAJORNUM=1); 
     Postsecondary certificates, awards or diplomas of less than one academic year 
     (CTOTALT,AWLEVEL=1,MAJORNUM=1) 

     Institutions are classified as Degree-granting (DEGGRANT=1) or Nondegree-granting(DEGGRANT=2) using level of offerings data reported on the Institutional Characteristics component. Degree-granting institutions offer an Associate's (LEVEL3=1),Bachelor's (LEVEL5=1, Master's (LEVEL7=1), Doctoral (LEVEL9=1), or a First-Professional (LEVEL10=1) degree. Any institution that offers only certificates are Nondegree-granting.

     Nondegree-granting institutions that offer a postbaccalaureate certificate (LEVEL6=1) or a post-master's certificate (LEVEL8=1) or a First-professional certificate (LEVEL11=1) are classified as Nondegree-granting, above the baccalaureate (INSTCAT=5).

     Nondegree-granting institutions that only offer certificates of less-than four academic years are classified as Nondegree-granting, sub-baccalaureate (INSTCAT=6).

     Degree-granting institutions whose highest degree granted are Associate's (HDEGOFFR=40) are classified as Degree-granting, Associate's and certificates (INSTCAT=4). (There are a few 4-year institutions that grant Postbaccalaureate or Post-master's or First-professional certificates in this category).

     Degree-granting institutions that do not grant a Bachelor's degree (LEVEL5=0) and do not grant an Associate's degree (LEVEL3=0) are classified as Degree-granting, graduate with no undergraduate degrees (INSTCAT=1).

     The remaining degree-granting institutions offer a bachelor's degree or an associate's degree, or both.
     For these institutions a percent of bachelor's degrees and all other degrees/certificates above the bachelor's degree of  total degrees and certificates is generated. If the percent is greater than 50, institutions are classified as Degree-granting, primarily baccalaureate or above (INSTCAT=2), If the percent is 50 or less, institutions are classified as Degree-granting, not primarily baccalaureate or above (INSTCAT=3).

     Inactive institutions (CYACTIVE in (2,3)) and administrative units (Sector=0) were coded as not applicable. All Non-Title IV institutions that did not respond to the  IC or Completions components were coded as not reported. 
     Degree-granting institutions whose completions data are reported with their parent institution were assigned the same code as the parent institution.
     New degree-granting institutions that report offering a bachelor's degree on the current institutional characteristics file for the upcoming academic year, and have not yet reported bachelor's degrees on the current completions that covers the previous academic year  June 1-July 30 were assigned as follows:

     If an institution reported a zero in any program (CIP code) for bachelor's degrees or above (indicating the level was offered) and did not report a zero for any program (CIP code) at any level below the bachelor's, the institution was classified as Degree-granting, primarily baccalaureate or above. If an institution reported a zero in any program (CIP code) at any level below the bachelor's degree and did not report a zero for any program (CIP code) for bachelor's degrees or above, the institution was classified as Degree-granting, not primarily baccalaureate or above.

     For institutions that reported zeros for bachelor's degrees of above and for levels below the bachelor's, the maximum number of programs by level was used to determine the primary classification."
     */
    private static int carnegie = 19;
    private static int size = 20;
    private static int countyname = 22;
    private static int longitude = 23;
    private static int latitude = 24;
    private static final CSV csv = CSV
            .separator(',') // delimiter of fields
            .create();       // new instance is immutable

    public static void main(String[] args) throws InterruptedException {
        final String[][] universities = new String[7504][25];
        csv.read("Post-Secondary_Universe_Survey_2010_-_Directory_information.csv", new CSVReadProc() {
            public void procRow(int rowIndex, String... values) {
                for (int i = 0; i < 25; i++) {
                    universities[rowIndex][i] = values[i];
                }
            }
        });
        
        for (int i = 0; i < 7504; i++) {
            System.out.println(universities[i][0]);
        }
        //TODO: splash screen/main menu need to look into lwjgl capabilities for this
        GameWorld gw = new GameWorld();

        gw.loadMap();
        gw.loadWorld();
        gw.start();

    }
}
