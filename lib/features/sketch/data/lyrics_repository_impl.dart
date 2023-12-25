import 'package:eleanor_rigby/features/sketch/data/lyrics_repository.dart';
import 'package:flutter/services.dart';

class LyricsRepositoryImpl extends LyricsRepository {
  static const MethodChannel _channel = MethodChannel('Example');

  @override
  Future<dynamic> generateLyrics(String input) async {
    return await _channel.invokeMethod('generateLyrics', {"input": input});
  }
}
