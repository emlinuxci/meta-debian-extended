# base recipe : meta/recipes-devtools/cmake/cmake_3.14.1.bb
# base branch : warrior
# base commit : 5da6073d47dcdc335d5c225a8945f5f85609580e

require ${COREBASE}/meta/recipes-devtools/cmake/cmake.inc

SRC_URI = ""

inherit debian-package
require recipes-debian/sources/cmake.inc
DEBIAN_QUILT_PATCHES = ""
FILESPATH_append = ":${COREBASE}/meta/recipes-devtools/cmake/cmake"

LIC_FILES_CHKSUM = "file://Copyright.txt;md5=f61f5f859bc5ddba2b050eb10335e013 \
                    file://Source/cmake.h;md5=4494dee184212fc89c469c3acd555a14;beginline=1;endline=3 \
"

SRC_URI += " \
           file://0002-cmake-Prevent-the-detection-of-Qt5_debian.patch \
           file://0003-cmake-support-OpenEmbedded-Qt4-tool-binary-names.patch \
           file://0004-Fail-silently-if-system-Qt-installation-is-broken.patch \
"

inherit cmake

DEPENDS += "curl expat zlib libarchive xz ncurses bzip2"

SRC_URI_append_class-nativesdk = " \
    file://OEToolchainConfig.cmake \
    file://environment.d-cmake.sh \
    file://0001-CMakeDetermineSystem-use-oe-environment-vars-to-load.patch \
"

# Strip ${prefix} from ${docdir}, set result into docdir_stripped
python () {
    prefix=d.getVar("prefix")
    docdir=d.getVar("docdir")

    if not docdir.startswith(prefix):
        bb.fatal('docdir must contain prefix as its prefix')

    docdir_stripped = docdir[len(prefix):]
    if len(docdir_stripped) > 0 and docdir_stripped[0] == '/':
        docdir_stripped = docdir_stripped[1:]

    d.setVar("docdir_stripped", docdir_stripped)
}

EXTRA_OECMAKE=" \
    -DCMAKE_DOC_DIR=${docdir_stripped}/cmake-${CMAKE_MAJOR_VERSION} \
    -DCMAKE_USE_SYSTEM_LIBRARIES=1 \
    -DCMAKE_USE_SYSTEM_LIBRARY_JSONCPP=0 \
    -DCMAKE_USE_SYSTEM_LIBRARY_LIBUV=0 \
    -DCMAKE_USE_SYSTEM_LIBRARY_LIBRHASH=0 \
    -DKWSYS_CHAR_IS_SIGNED=1 \
    -DBUILD_CursesDialog=0 \
    -DKWSYS_LFS_WORKS=1 \
"

do_install_append_class-nativesdk() {
    mkdir -p ${D}${datadir}/cmake
    install -m 644 ${WORKDIR}/OEToolchainConfig.cmake ${D}${datadir}/cmake/

    mkdir -p ${D}${SDKPATHNATIVE}/environment-setup.d
    install -m 644 ${WORKDIR}/environment.d-cmake.sh ${D}${SDKPATHNATIVE}/environment-setup.d/cmake.sh
}

FILES_${PN}_append_class-nativesdk = " ${SDKPATHNATIVE}"

FILES_${PN} += "${datadir}/cmake-${CMAKE_MAJOR_VERSION} ${datadir}/cmake ${datadir}/aclocal"
FILES_${PN}-doc += "${docdir}/cmake-${CMAKE_MAJOR_VERSION}"
FILES_${PN}-dev = ""

BBCLASSEXTEND = "nativesdk"
