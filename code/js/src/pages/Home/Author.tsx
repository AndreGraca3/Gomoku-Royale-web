export default function Author({
  name,
  avatarUrl,
  number,
  email,
  index = 0,
}: {
  name: string;
  avatarUrl: string;
  number: number;
  email: string;
  index?: number;
}) {
  return (
    <a
      style={{
        animationDelay: `${index * 0.1}s`,
      }}
      href={`mailTo:${email}`}
      className="flex items-center p-4 border opacity-0 rounded-md shadow-md hover:scale-110 transition-all duration-150 animate-pop-up"
    >
      <img
        src={avatarUrl}
        alt={`${name}'s avatar`}
        className="w-16 h-16 rounded-full mr-4"
      />
      <div>
        <h2 className="text-lg font-semibold">{name}</h2>
        <p className="text-gray-500">{number}</p>
      </div>
    </a>
  );
}
