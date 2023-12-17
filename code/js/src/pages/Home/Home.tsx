import Author from "./Author";

export default function Home() {
  const authors = [
    {
      name: "André Graça",
      avatarUrl: "/avatars/andre.jpg",
      number: 47224,
    },
    {
      name: "Diogo Santos",
      avatarUrl: "/avatars/diogo.jpg",
      number: 48459,
    },
    {
      name: "Daniel Caseiro",
      avatarUrl: "/avatars/daniel.jpg",
      number: 46052,
    },
  ];

  return (
    <div className="text-center p-20">
      <h1 className="text-4xl font-bold p-6 text-gr-yellow">
        Gomoku Royale Devs
      </h1>
      <div className="flex justify-evenly name-row">
        {authors.map((author) => (
          <Author
            key={author.number}
            name={author.name}
            avatarUrl={author.avatarUrl}
            number={author.number}
            email={`A${author.number}@alunos.isel.pt`}
          />
        ))}
      </div>
      <div className="flex justify-center items-center">
        <div className="p-16 items-center space-y-2">
          <p className="text-sm">
            Gomoku is a classic strategy board game that involves two players
            taking turns placing their pieces on a board. The objective is to
            create a row of five consecutive pieces either horizontally,
            vertically, or diagonally.
          </p>
          <p className="text-sm">
            For more information, check out the official{" "}
            <a
              href="https://en.wikipedia.org/wiki/Gomoku"
              target="_blank"
              rel="noopener noreferrer"
              className="underline text-blue-500 text-lg transition-colors duration-300 hover:text-blue-700"
            >
              Gomoku Wiki
            </a>
          </p>
        </div>
        <img src="/icon.png" className="w-20 h-20 justify-center items-center" />
      </div>
    </div>
  );
}
