# CoCube26Challenge
This repo contains the assignement for Hackerearth Cocube 26 Android Challenge.

1.
Cube26 PaymentPortals — a simplified way to search payment gateway!

[Problem Statement]

Develop a pseudo android application which would let users help list and search payment gateway details conveniently.

[Minimum Requirement]

— Use of Web API to fetch payment gateway details.

API Parameters:

payment_portals[] —> {id, name, image, description, branding, rating, setup_fee, transaction_fee, how_to_document, currencies}

— Visually interactive design to list payment gateway.

^ Awesome if you could manage to use of Material Design.
— Sort feature to sort payment gateway by rating or setup_fee.

— Submit Source code, Screenshots & compiled APK.

[Plus Point]

— Profile with extensive payment gateway details (branding, rating, transaction fee, how-to url, currencies).

— Implement a Search feature to help users search via name or currencies[].

— Deep link Support: Apart from the app you are building, you should create one more dummy app, with just one touch button. On the touch of that button, user should be able to open actual app.

tl;dr: Create an dummy app to open actual app!
— Feel free to use your favourite Android Development SDK and tools in development and design.

[Extra Work] (if time permits)

— Display number of payment gateway(s) available; also display API Hits.

— An option to filter list based on setup_fee (boolean).

— Implement a feature download documents (PDF) relevant to respective payment gateway.

^ Parallel/threaded-download feature; also try implementing progress-bar / notification.
— A feature to Like payment gateway and list those liked gateways.

^ Utilise SQLite to manage/store Like(s).
— Custom design, font and icons to make app more user-friendly.

— You may add portfolio activity comprising awesome work you have done on android.

— Use your imagination and add features which would make things easier for endusers.

[Guide]

— PaymentPortal API:

List payment gateway: http://hackerearth.0x10.info/api/payment_portals?type=json&query=list_gateway

Check API HIts: http://hackerearth.0x10.info/api/payment_portals?type=json&query=api_hits

— Android

• Material Design: http://developer.android.com/design/material/index.html

• Sharing: https://developer.android.com/training/sharing/index.html

• JSON: http://developer.android.com/reference/org/json/JSONObject.html

• SQLite: http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html

— For icons and subtle texture:

https://github.com/bperin/FontAwesomeAndroid

http://glyphsearch.com/ | http://www.flaticon.com/

http://subtlepatterns.com/thumbnail-view/

http://codebeautify.org/jsonviewer
