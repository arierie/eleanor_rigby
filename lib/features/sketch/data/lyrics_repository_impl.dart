import 'package:eleanor_rigby/features/sketch/data/lyrics_repository.dart';

class LyricsRepositoryImpl extends LyricsRepository {
  @override
  Future<dynamic> generateLyrics(String input) async {
    return "Output: $input";
  }
}
