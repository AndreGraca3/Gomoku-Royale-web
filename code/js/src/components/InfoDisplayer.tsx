export default function InfoDisplayer({ title, text, imgUrl }: { title: string; text?: string, imgUrl?: string }) {
  return (
    <div className="flex flex-col w-fit h-fit items-center bg-dark-theme-color text-white p-4 rounded-md animate-shine">
      <div className="flex justify-center items-center bg-theme-color p-2 rounded-md border border-gr-yellow">
        {imgUrl && <img className="w-14 h-14" src={imgUrl} alt="Rank Icon" /> }
        <span className="text-2xl">{text}</span>
      </div>
      <p className="text-sm mt-2">{title}</p>
    </div>
  );
}
