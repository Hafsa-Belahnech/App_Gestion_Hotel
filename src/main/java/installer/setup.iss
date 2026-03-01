[Setup]
AppName=Gestion Hôtel
AppVersion=1.0.0
AppPublisher= HN
DefaultDirName={pf}\GestionHotel
DefaultGroupName=Gestion Hôtel
OutputDir=output
OutputBaseFilename=GestionHotel_Setup
Compression=lzma
SolidCompression=yes

[Languages]
Name: "french"; MessagesFile: "compiler:Languages\French.isl"

[Files]
Source: "dist\GestionHotel-1.0.0-jar-with-dependencies.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "sql\hotel_db.sql"; DestDir: "{app}\sql"; Flags: ignoreversion
Source: "README.pdf"; DestDir: "{app}"; Flags: ignoreversion

[Icons]
Name: "{group}\Gestion Hôtel"; Filename: "{app}\GestionHotel-1.0.0-jar-with-dependencies.jar"; IconFilename: "{app}\icon.ico.png"
Name: "{commondesktop}\Gestion Hôtel"; Filename: "{app}\GestionHotel-1.0.0-jar-with-dependencies.jar"; IconFilename: "{app}\icon.ico.png"

[Run]
Filename: "java"; Parameters: "-jar ""{app}\GestionHotel-1.0.0-jar-with-dependencies.jar"""; Flags: postinstall nowait skipifsilent

[UninstallDelete]
Type: filesandordirs; Name: "{app}"