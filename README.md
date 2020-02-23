# NoSolo

BungeeCord plugin to require that players don't spend hours grinding by themselves.

Or as I call it: singleplayer on a server.

This plugin must be configured with a `main` server and a `lobby` server. When there is only one player online,
they get moved to the lobby server. I recommend a lobby server on creative mode with no rules.

When a second player joins, they both get moved to the main server. And when one leaves, the remaining player
gets moved back to lobby. Pretty simple concept.

To-do: make plugin properties configurable
