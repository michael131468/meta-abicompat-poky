SUMMARY  = "A collection of tools, libraries and tests for shader compilation"
DESCRIPTION = "The Shaderc library provides an API for compiling GLSL/HLSL \
source code to SPIRV modules. It has been shipping in the Android NDK since version r12b."
SECTION = "graphics"
HOMEPAGE = "https://github.com/google/shaderc"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRCREV = "0dbd899941a43ffd55df527d65128b3b66e75c9c"
SRC_URI = "git://github.com/google/shaderc.git;protocol=https;branch=main \
           file://0001-cmake-add-option-to-skip-build-the-examples.patch \
           file://0002-cmake-disable-building-external-dependencies-in-thir.patch \
           "
UPSTREAM_CHECK_GITTAGREGEX = "^(?P<pver>\d+(\.\d+)+)$"
S = "${WORKDIR}/git"

inherit cmake python3native pkgconfig

DEPENDS = "spirv-headers spirv-tools glslang"

EXTRA_OECMAKE = " \
    -DCMAKE_BUILD_TYPE=Release \
    -Dglslang_SOURCE_DIR=${STAGING_INCDIR}/glslang \
    -DSHADERC_SKIP_TESTS=ON \
    -DSHADERC_SKIP_EXAMPLES=ON \
"

do_configure_prepend() {
    cat <<- EOF > ${S}/glslc/src/build-version.inc
"${PV}\\n"
"$(pkg-config --modversion SPIRV-Tools)\\n"
"$(pkg-config --modversion glslang)\\n"
EOF
}

BBCLASSEXTEND = "native nativesdk"
