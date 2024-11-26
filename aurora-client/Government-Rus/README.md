# Aurora OS Application

Application provides access to the bills which are under consideration by Russian Government

The source code of the project is provided under
[the license](LICENSE.BSD-3-CLAUSE.md),
that allows it to be used in third-party applications.

## Project Structure

The project has a common structure
of an application based on C++ and QML for Aurora OS.

* **[io.github.gelassen.government.rus.Government-Rus.pro](io.github.gelassen.government.rus.Government-Rus.pro)** file
  describes the project structure for the qmake build system.
* **[icons](icons)** directory contains application icons for different screen resolutions.
* **[qml](qml)** directory contains the QML source code and the UI resources.
  * **[cover](qml/cover)** directory contains the application cover implementations.
  * **[icons](qml/icons)** directory contains the custom UI icons.
  * **[pages](qml/pages)** directory contains the application pages.
  * **[Government-Rus.qml](qml/Government-Rus.qml)** file
    provides the application window implementation.
* **[rpm](rpm)** directory contains the rpm-package build settings.
  **[io.github.gelassen.government.rus.Government-Rus.spec](rpm/io.github.gelassen.government.rus.Government-Rus.spec)** file is used by rpmbuild tool.
  It is generated from **[io.github.gelassen.government.rus.Government-Rus.yaml](rpm/io.github.gelassen.government.rus.Government-Rus.yaml)** file.
* **[src](src)** directory contains the C++ source code.
  * **[main.cpp](src/main.cpp)** file is the application entry point.
* **[translations](translations)** directory contains the UI translation files.
* **[io.github.gelassen.government.rus.Government-Rus.desktop](io.github.gelassen.government.rus.Government-Rus.desktop)** file
  defines the display and parameters for launching the application.