# base recipe: meta-oe/recipes-support/mime-support/mime-support_3.48.bb
# base branch: warrior
# base commit: 4afda201edfdf6e9ff56ec49dee30b889e3f0e56
SECTION = "base"
SUMMARY = "MIME files 'mime.types' & 'mailcap', and support programs"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=53c851e31d27c3ea8a6217073a5ff01c"

DEPENDS = "file"
RDEPENDS_${PN} = "perl"
RRECOMMENDS_${PN} = "file"

DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"

inherit debian-package
require recipes-debian/sources/mime-support.inc

S = "${WORKDIR}/${BPN}"

FILES_${PN} += " ${libdir}/mime"

docdir_append = "/${BPN}"

do_install () {
    install -d ${D}${sysconfdir}
    install -d ${D}${libdir}/mime/packages
    install -d ${D}${docdir}
    install -d ${D}${sbindir}
    install -d ${D}${bindir}
    install -d ${D}${mandir}/man1
    install -d ${D}${mandir}/man5
    install -d ${D}${mandir}/man8
    install -m 644 mime.types         ${D}${sysconfdir}/
    install -m 644 mailcap            ${D}${libdir}/mime/
    install -m 644 mailcap.order           ${D}${sysconfdir}/
    install -m 644 mailcap.man        ${D}${mandir}/man5/mailcap.5
    install -m 644 mailcap.order.man    ${D}${mandir}/man5/mailcap.order.5
#    install -m 755 install-mime        ${D}${sbindir}/
#    install -m 644 install-mime.man        ${D}${mandir}/man8/install-mime.8
    install -m 755 update-mime        ${D}${sbindir}/
    install -m 644 update-mime.man        ${D}${mandir}/man8/update-mime.8
    install -m 755 run-mailcap        ${D}${bindir}/
    install -m 644 run-mailcap.man        ${D}${mandir}/man1/run-mailcap.1
#    install -m 644 rfcs/*            ${D}${docdir}/
    install -m 644 debian/changelog        ${D}${docdir}/changelog.Debian
    install -m 644 debian/copyright        ${D}${docdir}/copyright
    install -m 755 debian-view        ${D}${libdir}/mime/
#    install -m 755 playaudio        ${D}${libdir}/mime/
#    install -m 755 playdsp            ${D}${libdir}/mime/
    install -m 644 mailcap.entries        ${D}${libdir}/mime/packages/mime-support
    cd ${D}${mandir}; gzip -9fv */*
    cd ${D}${docdir}; gzip -9v *
    cd ${D}${docdir}; gunzip copyright.gz
    cd ${D}${bindir}; ln -s run-mailcap see
    cd ${D}${bindir}; ln -s run-mailcap edit
    cd ${D}${bindir}; ln -s run-mailcap compose
    cd ${D}${bindir}; ln -s run-mailcap print
    cd ${D}${mandir}/man1; ln -s run-mailcap.1.gz see.1.gz
    cd ${D}${mandir}/man1; ln -s run-mailcap.1.gz edit.1.gz
    cd ${D}${mandir}/man1; ln -s run-mailcap.1.gz compose.1.gz
    cd ${D}${mandir}/man1; ln -s run-mailcap.1.gz print.1.gz
}

