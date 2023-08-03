# Configuration file for the Sphinx documentation builder.

from datetime import datetime
from pathlib import Path

DIR = Path(__file__).parent.resolve()

# -- Project information

project = 'XSMP Modeler'
current_year = datetime.now().year
copyright = f'{current_year}, Thales Alenia Space'
author = 'Thales Alenia Space'

# -- General configuration

extensions = [
    "breathe",
    "sphinx_copybutton",
    "sphinxcontrib.rsvgconverter",
    "sphinxcontrib.moderncmakedomain",
]

intersphinx_mapping = {
    'python': ('https://docs.python.org/3/', None),
    'sphinx': ('https://www.sphinx-doc.org/en/master/', None),
}
intersphinx_disabled_domains = ['std']

templates_path = ['_templates']

# -- Options for HTML output

html_theme = "furo"

# -- Options for EPUB output
epub_show_urls = 'footnote'
