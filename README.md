# Lexicanalytics

Lexicanalytics is a tool that helps researchers working in the area of text analysis to get lexical metrics and some others measurements. Aside, it gives some statistical outputs for those who may incorporate multiple productions analysis. It is free by nature and is available for use without restrictions, although it is focused in academic works and is released with a GPL License to protect the rights of reuse, for more information see the license.

## Highlighted functionalities


* Lexical and textual measures like number of words, number of lines, TTR. 
* Multiple productions analysis at once.
* Statistical measurements for the sample. 
* List and search of word occurrences in all or single text.
* Charts for productions overview and words occurrences.
* Reports ranking productions measures.
* General report save to text.


## Running

If you got a release, all you need is a relatively updated Java Runtime Environment ([JRE](https://www.java.com/en/download/)). So there is no problem running it on Linux, Windows or Mac. Then, run the jar file named lexicanalytics and the program should work.

**Warning:** OpenJDK versions may work if the user have built [openJFX](http://openjdk.java.net/projects/openjfx/) because the Lexicanalytics application is based on JavaFX. If the user is working with Oracle's JDK then JavaFX is already working.  

## Building and Distribution

To compile and develop, you'll need at least the JDK 8u40 because of some components. 
To produce a jar file, there may have alternative ways of doing this, but we used [Eclipse Luna](https://eclipse.org/) and a plugin called [e(fx)clipse](https://www.eclipse.org/efxclipse/index.html) following the steps above:

1.  Within the Project's root directory, locate and open "build.fxbuild" using the IDE;
2.  You may see a option called "Generate ant build.xml only" that should appears as a link;
3.  After the processing the "build" directory is created;
4.  Take a look at the file "build.xml" inside the "build" directory (a comprehensive xml file that may have no problems, some people across the web experienced resources issues but didn't notice something like that in this project until now);
5.  Right click on it and choose "Run As" followed by "Ant Build";
6.  After the procedure the distribution jar is inside the "dist" directory (within "build").

## License

Lexicanalytics is a free software developed and distributed under the GNU Public License Version 3. In the Project's root directory you can see the LICENSE. 