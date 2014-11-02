/*
 * Copyright (c) 2014 acmi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package acmi.l2.clientmod.unreal.Core;

import acmi.l2.clientmod.io.DataInput;
import acmi.l2.clientmod.io.UnrealPackageReadOnly;
import acmi.l2.clientmod.unreal.bytecode.BytecodeReader;
import acmi.l2.clientmod.unreal.bytecode.NativeFunctionsHardcode;
import acmi.l2.clientmod.unreal.properties.PropertiesUtil;

import java.io.IOException;

public class Struct extends Field {
    public final int scriptText;
    public final int child;
    public final int friendlyName;
    public final int line;
    public final int textPos;
    public final int scriptSize;

    public Struct(DataInput input, UnrealPackageReadOnly.ExportEntry entry, PropertiesUtil propertiesUtil) throws IOException {
        super(input, entry, propertiesUtil);

        scriptText = input.readCompactInt();
        child = input.readCompactInt();
        friendlyName = input.readCompactInt();
        input.readCompactInt();
        line = input.readInt();
        textPos = input.readInt();
        scriptSize = input.readInt();
        if (scriptSize != 0) {
            //TODO code statements
            BytecodeReader bytecodeReader = new BytecodeReader(input, scriptSize, entry.getUnrealPackage(), new NativeFunctionsHardcode());
            System.out.println(entry);
            while (bytecodeReader.hasNext()) {
                System.out.println("\t" + bytecodeReader.next());
            }
        }
    }

    public UnrealPackageReadOnly.Entry getScritpText() {
        return getEntry().getUnrealPackage().objectReference(scriptText);
    }

    public UnrealPackageReadOnly.Entry getChild() {
        return getEntry().getUnrealPackage().objectReference(child);
    }

    public String getFriendlyName() {
        return getEntry().getUnrealPackage().getNameTable().get(friendlyName).getName();
    }

    @Override
    public String toString() {
        return getEntry() + ": " + getClass().getSimpleName() + "[" + getFriendlyName() + ']';
    }
}
