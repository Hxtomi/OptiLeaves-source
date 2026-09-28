import json
from pathlib import Path
from urllib.request import Request, urlopen

PROJECT_IDS = ("ChDudAun", "5tucHyQx")
BADGE = Path(__file__).resolve().parents[1] / "badges" / "modrinth.svg"


def main():
    total = 0
    for project_id in PROJECT_IDS:
        request = Request(
            f"https://api.modrinth.com/v2/project/{project_id}",
            headers={"User-Agent": "Hxtomi/OptiLeaves-source (GitHub download badge)"},
        )
        with urlopen(request, timeout=30) as response:
            project = json.load(response)
        count = project["downloads"]
        if project["id"] != project_id or type(count) is not int or count < 0:
            raise ValueError("Unexpected Modrinth project response")
        total += count

    label = f"{total:,}"
    width = max(78, len(label) * 8 + 20)
    svg = (
        f'<svg xmlns="http://www.w3.org/2000/svg" width="{88 + width}" height="20" '
        f'role="img" aria-label="Modrinth: {label} downloads">\n'
        f'  <title>Modrinth: {label} downloads, both projects combined</title>\n'
        f'  <clipPath id="r"><rect width="{88 + width}" height="20" rx="3"/></clipPath>\n'
        f'  <g clip-path="url(#r)"><rect width="88" height="20" fill="#555"/>'
        f'<rect x="88" width="{width}" height="20" fill="#177c47"/></g>\n'
        f'  <g fill="#fff" text-anchor="middle" font-family="Verdana,DejaVu Sans,sans-serif" font-size="11">\n'
        f'    <text x="44" y="14">Modrinth</text><text x="{88 + width / 2:g}" y="14">{label}</text>\n'
        f'  </g>\n</svg>\n'
    )
    BADGE.parent.mkdir(parents=True, exist_ok=True)
    BADGE.write_text(svg, encoding="utf-8")
    print(f"Modrinth combined downloads: {total}")


if __name__ == "__main__":
    main()
