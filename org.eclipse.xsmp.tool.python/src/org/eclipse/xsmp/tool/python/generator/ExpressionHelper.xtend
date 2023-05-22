package org.eclipse.xsmp.tool.python.generator

import com.google.inject.Inject
import org.eclipse.xsmp.util.XsmpUtil
import org.eclipse.xsmp.xcatalogue.Expression
import org.eclipse.xsmp.xcatalogue.Type
import org.eclipse.xsmp.util.PrimitiveTypeKind

class ExpressionHelper {

    @Inject
    XsmpUtil util

    def String getValue(Expression expression, Type type) {
        getValue(expression, util.getPrimitiveTypeKind(type))
    }

    def String getValue(Expression expression, PrimitiveTypeKind primitiveType) {
        switch (primitiveType) {
            case BOOL:
                return util.getBoolean(expression) ? "True" : "False"
            case FLOAT32:
                return Float.toString(util.getFloat32(expression))
            case FLOAT64:
                return Double.toString(util.getFloat64(expression))
            case INT8:
                return Integer.toString(util.getInt8(expression))
            case INT16:
                return Integer.toString(util.getInt16(expression))
            case INT32:
                return Integer.toString(util.getInt32(expression))
            case INT64:
                return Long.toString(util.getInt64(expression))
            case UINT8:
                return Integer.toString(util.getUInt8(expression))
            case UINT16:
                return Integer.toString(util.getUInt16(expression))
            case UINT32:
                return Long.toString(util.getUInt32(expression))
            case UINT64:
                return util.getUInt64(expression).toString
            case ENUM:
                return util.fqn(util.getEnumerationLiteral(expression)).toString
            case CHAR8:
                return "'" + util.getChar8(expression) + "'"
            case STRING8:
                return "\"" + util.getString8(expression) + "\""
            default:
                return ""
        }
    }
}
