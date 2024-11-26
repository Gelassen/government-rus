/*******************************************************************************
**
** Copyright (C) 2022 io.github.gelassen.government.rus
**
** This file is part of the Aurora OS Application project.
**
** Redistribution and use in source and binary forms,
** with or without modification, are permitted provided
** that the following conditions are met:
**
** * Redistributions of source code must retain the above copyright notice,
**   this list of conditions and the following disclaimer.
** * Redistributions in binary form must reproduce the above copyright notice,
**   this list of conditions and the following disclaimer
**   in the documentation and/or other materials provided with the distribution.
** * Neither the name of the copyright holder nor the names of its contributors
**   may be used to endorse or promote products derived from this software
**   without specific prior written permission.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
** AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
** FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
** IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
** OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
** PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
** LOSS OF USE, DATA, OR PROFITS;
** OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
** WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
** (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
** EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
**
*******************************************************************************/

import QtQuick 2.0
import Sailfish.Silica 1.0

Page {
    id: mainPage
    objectName: "mainPage"
    width: parent.width
    height: parent.height
    allowedOrientations: Orientation.Landscape

    property bool isTablet: ApplicationWindow.primaryItem.width > 600

//    PageHeader {
//        objectName: "pageHeader"
//        title: qsTr("Russian Government")
//        extraContent.children: [
//            IconButton {
//                objectName: "aboutButton"
//                icon.source: "image://theme/icon-m-about"
//                anchors.verticalCenter: parent.verticalCenter

//                onClicked: pageStack.push(Qt.resolvedUrl("AboutPage.qml"))
//            }
//        ]
//    }

    // Container for Main and Detail view
    Rectangle {
        id: splitContainer
        width: parent.width
        height: parent.height
        color: "transparent"

        // Main Panel (List of items)
        ListView {
            id: mainPanel
            width: isTablet ? parent.width * 0.3 : parent.width
            height: parent.height
            model: ListModel {
                ListElement { title: "Item 1" }
                ListElement { title: "Item 2" }
                ListElement { title: "Item 3" }
            }

            delegate: Item {
                width: parent.width
                height: Theme.itemSizeSmall
                Text {
                    anchors.centerIn: parent
                    text: model.title
                }
                MouseArea {
                    anchors.fill: parent
                    onClicked: {
                        if (isTablet) {
                            detailPanel.setText(model.title)
                        } else {
                            // Navigate to Detail Page for Phones
                            pageStack.push(detailPage, { "detailText": model.title })
                        }
                    }
                }
            }
        }

        // Detail Panel (For Tablets)
        Rectangle {
            id: detailPanel
            width: isTablet ? parent.width * 0.7 : 0
            height: parent.height
            visible: isTablet
            color: "lightgray"

            Text {
                id: detailText
                anchors.centerIn: parent
                text: "Select an item"
            }

            function setText(text) {
                detailText.text = "Detail: " + text;
            }
        }
    }

    // Detail Page (For Phones)
    Page {
        id: detailPage
        property string detailText
        Text {
            anchors.centerIn: parent
            text: detailPage.detailText
        }
    }
}
