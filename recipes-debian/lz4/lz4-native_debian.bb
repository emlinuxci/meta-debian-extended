# base recipe: meta/recipes-support/lz4/lz4_1.8.3.bb
# base branch: warrior
# base commit: bb2d6cc85d558b2292a1a76d42c7cb1ff9713332

inherit native

# native class above seems to overwrite CPPFLAGS=. So, please include
# lz4.inc after inheriting native class.
require lz4.inc
