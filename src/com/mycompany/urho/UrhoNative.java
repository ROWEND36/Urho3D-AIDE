//
// Copyright (c) 2008-2017 the Urho3D project.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

package com.mycompany.urho;

import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import org.libsdl.app.SDLActivity;

import java.io.IOException;
import java.util.*;
import android.app.*;
import android.content.*;

public class UrhoNative extends SDLActivity {

    public static final String SCRIPTS = "scripts";
    private static final String TAG = "Urho3D";
    private static String[] mArguments = new String[0];

    @Override
    protected String[] getArguments() {
        return mArguments;
    }

    @Override
    protected boolean onLoadLibrary(ArrayList<String> libraryNames) {
        // Ensure "Urho3D" shared library (if any) and "Urho3DPlayer" are being sorted to the top of the list
        // Also ensure STL runtime shared library (if any) is sorted to the top most entry
        Collections.sort(libraryNames, new Comparator<String>() {
            private String sortName(String name) {
                return name.matches("^\\d+_.+$") ? name : (name.matches("^.+_shared$") ? "0000_" : "000_") + name;
            }

            @Override
            public int compare(String lhs, String rhs) {
                return sortName(lhs).compareTo(sortName(rhs));
            }
        });
		Log.e(libraryNames.get(0),libraryNames.get(1));
        // Determine the intention
        
        return super.onLoadLibrary(libraryNames);
    }

	@Override
	public void onBackPressed()
	{
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
		dlgAlert.setMessage("Confirm Exit?");
							//+ System.getProperty("line.separator")
		dlgAlert.setTitle("SDL Error");
		dlgAlert.setPositiveButton("Yes",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close current activity
					nativeQuit();
					if (SDLActivity.mSDLThread != null) {
						try {
							SDLActivity.mSDLThread.join();
						} catch(Exception e) {
							Log.v(TAG, "Problem stopping thread: " + e);
						}}
					handleNativeExit();
					initialize();
				}
			});
		dlgAlert.setCancelable(false);
		dlgAlert.create().show();
		return;
	}
}
