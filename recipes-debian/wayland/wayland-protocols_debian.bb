# base recipe: meta/recipes-graphics/wayland/wayland-protocols_1.17.bb
# base branch: warrior
# base commit: 8e211c9a6187d859eec1fa7607503d77e459daaf

SUMMARY = "Collection of additional Wayland protocols"
DESCRIPTION = "Wayland protocols that add functionality not \
available in the Wayland core protocol. Such protocols either add \
completely new functionality, or extend the functionality of some other \
protocol either in Wayland core, or some other protocol in \
wayland-protocols."
HOMEPAGE = "http://wayland.freedesktop.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=c7b12b6702da38ca028ace54aae3d484 \
                    file://stable/presentation-time/presentation-time.xml;endline=26;md5=4646cd7d9edc9fa55db941f2d3a7dc53"

inherit debian-package
require recipes-debian/sources/${BPN}.inc

DEBIAN_QUILT_PATCHES = ""

UPSTREAM_CHECK_URI = "https://wayland.freedesktop.org/releases.html"

inherit allarch autotools pkgconfig

PACKAGES = "${PN}"
FILES_${PN} += "${datadir}/pkgconfig/wayland-protocols.pc"
