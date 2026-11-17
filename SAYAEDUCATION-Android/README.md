# SAYAEDUCATION Android test app

This is the Android application source for the SAYAEDUCATION student portal, running https://sayaeducation-preview.vercel.app/ with the existing Supabase authentication and YouTube classroom.

## Build

Use Android Studio (JDK 17, Android SDK 35) or GitHub Actions (`Build Android Debug APK`) to build a test APK.

The website's student enrollment and payment verification stay server-controlled. An unlisted YouTube livestream link can still be shared externally.

This is a website-backed Android app, and has not been approved for distribution on Google Play. Store publication needs policy, billing and quality review. No Razorpay payment credentials or Supabase server secrets belong in this repository.
