let
  sources = import nix/sources.nix;
  pkgs = import sources.nixpkgs { };
  gradleWrapper = pkgs.writeShellScriptBin "gradle" ''
    ${toString ./.}/gradlew "$@"
  '';
in pkgs.mkShell {
  buildInputs = with pkgs; [
    sources.nixpkgs # include pinned nixpkgs in GC root as well
    jdk8
    niv
    gradleWrapper
    yarn
    nodejs
  ];
}
