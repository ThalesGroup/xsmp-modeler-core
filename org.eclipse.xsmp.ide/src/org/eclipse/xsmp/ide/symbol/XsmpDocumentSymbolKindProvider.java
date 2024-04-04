/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.symbol;

import static org.eclipse.xsmp.model.xsmp.XsmpPackage.ARRAY;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.ASSOCIATION;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.CATALOGUE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.CLASS;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.CONSTANT;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.ENTRY_POINT;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.ENUMERATION;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.ENUMERATION_LITERAL;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.EVENT_SINK;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.EVENT_SOURCE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.EVENT_TYPE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.EXCEPTION;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.FIELD;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.FLOAT;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.INTEGER;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.INTERFACE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.MODEL;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.NAMESPACE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.OPERATION;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.PARAMETER;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.PROFILE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.PROFILE_REFERENCE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.PROJECT;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.PROJECT_REFERENCE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.PROPERTY;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.SERVICE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.SOURCE_PATH;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.STRING;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.STRUCTURE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.TOOL;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.TOOL_REFERENCE;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolKindProvider;

import com.google.inject.Singleton;

@Singleton
public class XsmpDocumentSymbolKindProvider extends DocumentSymbolKindProvider
{
  @Override
  protected SymbolKind getSymbolKind(EClass clazz)
  {

    return switch (clazz.getClassifierID())
    {
      case ARRAY -> SymbolKind.Array;
      case CONSTANT -> SymbolKind.Constant;
      case ENUMERATION -> SymbolKind.Enum;
      case ENUMERATION_LITERAL -> SymbolKind.EnumMember;
      case EVENT_TYPE, EVENT_SINK, EVENT_SOURCE -> SymbolKind.Event;
      case FIELD -> SymbolKind.Field;
      case ENTRY_POINT, ASSOCIATION -> SymbolKind.Object;
      case OPERATION -> SymbolKind.Method;
      case INTERFACE -> SymbolKind.Interface;
      case FLOAT, INTEGER -> SymbolKind.Number;
      case CATALOGUE, PROJECT, TOOL, PROFILE -> SymbolKind.File;
      case PROPERTY -> SymbolKind.Property;
      case PARAMETER -> SymbolKind.Variable;
      case STRING -> SymbolKind.String;
      case STRUCTURE -> SymbolKind.Struct;
      case NAMESPACE -> SymbolKind.Namespace;
      case CLASS, EXCEPTION, MODEL, SERVICE -> SymbolKind.Class;
      case SOURCE_PATH -> SymbolKind.Package;
      case TOOL_REFERENCE -> SymbolKind.Property;
      case PROFILE_REFERENCE -> SymbolKind.Class;
      case PROJECT_REFERENCE -> SymbolKind.Variable;
      default -> SymbolKind.Property;
    };

  }
}
