# CSE News
<html>
<p align="center">
<img src="https://i.imgur.com/UcoQpln.png" width="300" height="300">
</p>
<h4>
<h3>Content:</h3>
<li><a href="#description">Program description.</a></li>
<li><a href="#works">How it works.</a></li>
<li><a href="#preview">Program preview.</a></li>
<li><a href="#wrap">How to wrap the .jar into .exe file.</a></li>
<li><a href="#issues">Issue/Bug Policy.</a></li>
<li><a href="#compiledversion">Download compiled version.</a></li>
<li><a href="#libs">Libraries used.</a></li>
<hr>
<a name="description">
<h3>Description:</h3>
CSE News is an application created to help <a href="http://cs.uoi.gr/">Computer Science Engineering</a> Students of <a href="http://www.uoi.gr/">UOI (University of Ioannina)</a>
stay updated with department's news. It also provides some other small services, such opening the most common or a professor's website with a single click. It runs in Tray System
 and informs the user for any new announcement of the department. For now it is only tested in <u><i>Windows 7 Ultimate x64</i></u> but it should run flawlessly in any <u><i>Windows</i></u> version.
</a>
<hr>
<a name="works">
<h3>How it works:</h3>
It parses periodically the HTML code of the main announcements page and stores them locally. In case of any change user is being informed
 with a small popup message. Once in 60 days, it parses the professors list (again from departments main website) and stores their information locally aswell.
 All data are being saved in C:\Users\user_name\AppData\Roaming\CSE News\.
</a>
<hr>
<a name="preview">
<h3>Preview:</h3>
<img src="https://i.imgur.com/q8dSe8N.png">
<img src="https://i.imgur.com/TwRw1nH.png">
</a>
<hr>
<a name="issues">
<h3>Issue/Bug Policy</h3>
In case you have used CSE News and you have spot any kind of bug/issue, please <a href="hhttps://github.com/GiorgosZougianos/CSNews/issues/new">create an issue on GitHub</a> or send an e-mail to <i>g.zougianos@gmail.com</i>.
</a>
<hr>
<a name="wrap">
<h3>How to wrap the compiled jar to exe:</h3>
<li>Unzip <a href="https://github.com/GiorgosZougianos/CSNews/blob/master/wrapper.rar">wrapper.rar</a>
<li>Launch launch4j.exe inside the unzipped folder.</li>
<li>Click "Open configuration or import", second icon (blue folder) on the toolbar.</li>
<li>Choose CSNews.xml file.</li>
<li>Click "Build Wrapper", fourth icon (gray gear) on the toolbar.</li>
<li>Run CSNews.exe</li>
</a>
<hr>
<a name="compiledversion">
<h3>Download Compiled Version: </h3>
<li><a href="https://github.com/GiorgosZougianos/CSNews/raw/master/release/CSNews.exe">.exe</a> version.</li>
<li><a href="https://github.com/GiorgosZougianos/CSNews/raw/master/release/CSNews.jar">.jar</a> version.</li>
</a>
<hr>
<a name="libs">
<h3>Libraries used: </h3>
<a href="https://github.com/jhy/jsoup">JSOUP</a>
</a>
</h4>
</html>
